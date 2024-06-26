use master 
go
--check if a database with this name already exists
IF (EXISTS (SELECT name 
FROM master.dbo.sysdatabases 
WHERE ( name  = N'PREMIERECO' 
)))
 begin
 drop database PREMIERECO
 end
go
create database PREMIERECO
go
USE [PREMIERECO]
GO
CREATE TABLE [dbo].[EMPLOYEE](
	[EMP_Num] [int] NOT NULL,
	[LNAME] [varchar](15) NOT NULL,
	[FNAME] [varchar](15) NOT NULL,
	[STREET] [varchar](20) NOT NULL,
	[CITY] [varchar](10) NOT NULL,
	[EMP_STATE] [nchar](2) NOT NULL,
	[ZIP] [nchar](9) NOT NULL,
	BirthDate date not null,
	[HIRE_Date] [date] NOT NULL,
	EmpType int not null,        --1 salesperson  2 manager  3 clerk 
	loginName varchar(20) null,
	userPassword varchar(15) null
 CONSTRAINT [PK_EMP] PRIMARY KEY  
(
	[EMP_Num] )
	);

INSERT into Employee VALUES (20, 'Kaiser', 'Valerie', '624 Randall', 'Grove', 'FL', '33321','1968-09-03', '1995-05-20',1,null,null )
INSERT into Employee VALUES (35, 'Hull', 'Richard', '532 Jackson', 'Sheldon', 'FL', '33553', '1979-03-22', '2000-04-15',1,null,null)
INSERT into Employee VALUES (65, 'Perez', 'Juan', '1626 Taylor', 'Fillmore', 'FL', '33336', '1982-07-27', '2002-02-12',1,null,null)
INSERT INTO Employee values (10, 'Richardson','Richard', '4 Throop Av','Brooklyn','NY','11221','1970-08-20','2017-05-15',2,null,null)
insert into Employee values (15, 'Merrin','Lisa', '2416 Newkirk Ave','Brooklyn','NY','11226','1980-05-12','2018-03-19',3,null,null)

go

CREATE TABLE [dbo].[SALESREP](
	[REP_NUM] int NOT NULL,
	[COMMISSION] [smallmoney] NOT NULL,
	[COMM_RATE] decimal(4,2) NOT NULL default (0.0),
 CONSTRAINT [PK_SALESREP] PRIMARY KEY (REP_NUM ),
 constraint [FK_SALESREP_EMP] foreign key (REP_NUM) references 
    Employee(EMP_NUM),
	constraint [CHK_RATE] check (COMM_RATE >= 0.0)
);
GO



go
CREATE TABLE [dbo].[PART](
	[PART_NUM] [varchar](10) NOT NULL,
	[PART_DESCRIPTION] [varchar](30) NOT NULL,
	[UNITS_ON_HAND] [smallint] NOT NULL,
	[CATEGORY] [nchar](2) NOT NULL,  --AP appliance  HW - housewares   --SG sporting goods
	[WAREHOUSE] [smallint] NOT NULL,
	[PRICE] [smallmoney] NOT NULL,
 CONSTRAINT [PK_PART_1] PRIMARY KEY ([PART_NUM] )
);
GO




CREATE TABLE [dbo].[CUSTOMER](
	[CUST_NUM] int NOT NULL,
	[CUST_NAME] [varchar](40) NOT NULL,
	[CUST_STREET] [varchar](30) NOT NULL,
	[CUST_CITY] [varchar](25) NOT NULL,
	[CUST_STATE] [varchar](10) NOT NULL,
	[CUST_ZIP] [nchar](10) NOT NULL,
	[CUST_BALANCE] [smallmoney] NOT NULL,
	[CREDIT_LIMIT] decimal(8,2) NOT NULL,
	[REP_NUM] int NOT NULL,
	[PHONE] [char](10) NULL,
	[BEGIN_DATE] [date] NULL,
 CONSTRAINT [PK_CUSTOMER] PRIMARY KEY (	[CUST_NUM] ),
 constraint [PK_CUSTOMER_SALESREP] foreign key (REP_NUM) references
    SALESREP(REP_NUM),
	constraint [CHK_CUST_BALANCE] check
	  (CUST_BALANCE <= CREDIT_LIMIT)
 );
GO

              
go

go
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[generateEMPNum]
   ON  [dbo].[EMPLOYEE] 
   instead of INSERT
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	Declare @EmpNum  int;
	declare @curEmpNum int;
	--this trigger only works if the employee table already has some employee records

	select @curEmpNum =
	  max(Emp_num) from employee
	  
	set @empNum = @curEmpNum + 1  ;
	
	--create a temporary table with
	--record that is being inserted into
	--salesrep
	select * into #inserted from inserted
	
	--modify the temporary table
	--assigning salesrep the number we
	--generated
	update #inserted
	set emp_num = @empNum
	
	--complete the insert by inserting this
	--record into the salesrep table from the
	--temporary table
    insert into employee
     select * from #inserted
    

END
GO
/****** Object:  Table [dbo].[ORDERS]    Script Date: 04/22/2015 12:59:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE TABLE [dbo].[ORDERS](
	[ORDER_NUM] int identity (21600,1) NOT NULL,
	[ORDER_DATE] [date] NOT NULL,
	[CUST_NUM] int NOT NULL,
 CONSTRAINT [PK_ORDER] PRIMARY KEY  
(	[ORDER_NUM] ),
 constraint [FK_ORDERS_CUSTOMER] foreign key
   (CUST_NUM) references Customer(CUST_NUM)
);
GO

go
CREATE TABLE [dbo].[ORDER_LINE](
	[ORDER_NUM] int NOT NULL,
	[PART_NUM] [varchar](10) NOT NULL,
	[QTY_ORDERED] [smallint] NOT NULL,
	[QUOTED_PRICE] [smallmoney] NOT NULL,
	[Subtotal]   as qty_ordered * quoted_price persisted, --calculated field 
	[Modified_Date] [date] NULL,
 CONSTRAINT [PK_ORDER_LINE] PRIMARY KEY (	[ORDER_NUM] ASC,
	[PART_NUM] ASC),
	constraint [FK_ORDERLINE_ORDERS] foreign key (ORDER_NUM)
	  references Orders(ORDER_NUM),
	  constraint [FK_ORDERLINE_PARTS] foreign key (PART_NUM)
	  references PART(PART_NUM)
);
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[UpdatePartsInv]
   ON  [dbo].[ORDER_LINE]
   AFTER INSERT,DELETE,UPDATE
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

   Declare @PartNum nchar(10);
   Declare @qty smallint;
   Declare @qtyAvail smallint;
    if exists (select * from inserted)
    begin   --process an insert of a record
    if exists (select * from deleted)  --this is really an update
      begin       -- add the qty back to units on hand
       select @PartNum = Part_Num from deleted
       select @qty = qty_ordered from deleted
       update part
         set units_on_hand = units_on_hand + @qty
         where part_num = @PartNum
         
     end
      --continue to process the insert 
      select @PartNum = Part_Num from inserted
      select @qty = qty_ordered from inserted
   
      select @qtyAvail = units_on_hand from part
          where Part_Num = @PartNum
      --check if have enough inventory
      if @qtyAvail < @qty
          begin;
             throw 50010,'insufficient inventory',1
             
          end     
       else
         --modify the part table
          update part
            set units_on_hand = units_on_hand - @qty
             where Part_Num = @PartNum   
    end   --process an insert or update
    else --just processing a delete
      begin
        select @PartNum = Part_Num from deleted
        select @qty = qty_ordered from deleted
        update part
         set units_on_hand = units_on_hand + @qty
         where part_num = @PartNum
      end  
   
     
  
        
   

END
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE TRIGGER [dbo].[UpdateCustBalance]
   ON  [dbo].[ORDER_LINE] 
   AFTER  INSERT,DELETE,UPDATE
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	declare @orderNum int;
	declare @custNum int;
	declare @qty int;--how many were ordered
	declare @quotedPrice smallmoney;--price per item
	declare @subtotal smallmoney;--total charge for this item
	if exists (select * from inserted)
	  --a new record is being inserted into orderline
	        
	  begin
	        --find which order is being processed
	        select @ordernum = order_num 
			  from inserted
			  --find which customer placed this order
	        select @custNum =
	            cust_num from orders
	                where order_num = @orderNum
	    if exists (select * from deleted)
	    --part of an order is being updated
	       
	    begin 
		    select @qty = QTY_ORDERED from deleted
	        select @quotedPrice = QUOTED_PRICE from deleted
	        
	        set @subtotal = @quotedPrice * @qty
	       --find and modify the customer record of this customer
	        update customer
	        set cust_balance = cust_balance - @subtotal
	        where cust_num = @custNum        
	    end  --end update
	    select @qty = qty_ordered from inserted
	    select @quotedPrice = quoted_price from inserted
	   
	    set @subtotal = @qty * @quotedPrice --how much
	        -- will be owed for this order
	    declare @remainCredit smallmoney;
	    select @remainCredit =
	         credit_limit - cust_balance
	         from customer
	            inner join orders
	                on customer.cust_num = 
	                  orders.cust_num
	                  where orders.order_num=
	                     @orderNum
	    if @subtotal > @remainCredit
	        begin;
	            throw 50011,'insufficient credit',1
	            
	        end   
		else
		  begin   --apply order subtotal to the customer balance
		     update customer
			 set cust_balance = cust_balance + @subtotal
			 where cust_num = @custnum
		  end	              
	                         
 end --end insert or update
else --part of an order is being cancelled
	     
 begin
	        select @qty = qty_ordered from deleted
	        select @quotedPrice = quoted_price from deleted
	        select @orderNum = order_num from deleted
	        set @subtotal = @quotedPrice * @qty
	        --find which customer placed this order
	        select @custNum =
	            cust_num from orders
	                where order_num = @orderNum
	        --find the customer record of this customer
	        update customer
	        set cust_balance = cust_balance - @subtotal
	        where cust_num = @custNum        
	    end  --end update
    
      
END
GO


CREATE TRIGGER [dbo].[UpdateSalesRepCommission]
   ON  [dbo].[ORDER_LINE] 
   AFTER  INSERT,DELETE,UPDATE
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	declare @orderNum int;
	declare @custNum int;
	declare @salesRepNum int;
	declare @rate decimal(4,2)
	declare @qty int;--how many were ordered
	declare @quotedPrice smallmoney;--price per item
	declare @subtotal smallmoney;--total charge for this item
	declare @earnedCommission smallmoney

	declare @TransCode char(1)

	--determine the type of transaction that has occurred

	if exists(select * from inserted) 
	   begin
	     if exists(select * from deleted)
		    set @TransCode = 'U'    --modifying a record
         else 
		    set @TransCode = 'I'    --inserting a new record
	   end
    else
	   set @TransCode = 'D'    --deleting a record
	
	if @TransCode = 'I'
	  --a new record is being inserted into orderline table
	        
	  begin
	        --find which order is being processed
	        select @ordernum = order_num 
			  from inserted
			  --find which customer placed this order
	        select @salesRepNum =
	            rep_num from customer inner join orders 
				   on orders.cust_num = customer.cust_num
	                where order_num = @orderNum 
            --calculate the commission based on the salesperson's rate of commission
			select @rate = comm_rate from salesrep
			   where rep_num = @salesRepNum
			--retrieve information about this portion of the order
			select @qty = 
			    qty_ordered from inserted
            select @quotedPrice =
			    quoted_price from inserted

            set @earnedCommission =
			   @rate * (@qty * @quotedPrice)
            update salesrep    --add the earned commission to this salesperson's record
			   set commission = commission + @earnedCommission
			     where rep_num = @salesRepNum
       end
     else
	    begin
		   if @TransCode = 'D'
		     begin 
			      --find out which order is being processed
				  select @orderNum = order_num from deleted

				  --part of an order is being deleted
				  --salesperson's commission must be adjusted , loses the commission
				  --find out how much was ordered and for what price
				  select @qty =
				    qty_ordered, @quotedPrice = quoted_price  from deleted
				  --which salesperson placed this order
				  select @salesRepNum =
				   rep_num from customer inner join orders on
				     customer.cust_num = orders.cust_num
					  where order_num = @orderNum
                  --calculate the commission that was earned

				  select @rate = comm_rate from salesrep
				     where rep_num = @salesRepNum    
					  --if the commission rate changed since this order was place would have to look it up in a history table
                 --calculate the commission earned on this part of the order
				 set @earnedcommission = @rate * (@qty * @quotedPrice)
				 -- modify the salesperson's record to deduc this amount from commission earned
				 update salesrep 
				 set commission = commission - @earnedcommission
				 where rep_num = @salesRepNum

				 
			 end
			 else --transaction is Update 
			   begin 
			       --must add the new commission earned to the salesperson's commission
				   --must subtract the commission earned from cancelled order from the salesperson's commission

				   --which order
				   select @orderNum = order_num from inserted
				   --which salesperson
				   select @salesRepNum = rep_num 
				     from customer inner join orders
					  on customer.cust_num = orders.cust_num
					  where order_num = @orderNum
				    --what is this salesperson's commission rate
                   select @rate = comm_rate from salesrep 
				      where rep_num = @salesRepNum
				   --previous order
				   select @qty = qty_ordered , @quotedPrice = quoted_price from deleted
				   declare @unearnedComm smallmoney
				   set @unearnedComm = @rate * ( @qty * @quotedPrice)
				   
				  
				   --current order
				   select @qty = qty_ordered, @quotedPrice = quoted_price from inserted
				   set @earnedCommission = @rate * (@qty * @quotedPrice)

				   -- subtract unearned commission from earned commission
				   set @earnedCommission = @earnedCommission - @unearnedComm
				   --now update the salesperson's record
				   update salesrep
				     set commission = commission + @earnedCommission
					 where rep_num = @salesRepNum

			   end
		end
	  
	 
	      
    
      
END
GO

INSERT into [SALESREP] VALUES (20, 0, 0.05)
INSERT into [SALESREP]  VALUES (35,0, 0.08)
INSERT into [SALESREP] VALUES (65,0, 0.07)
go

--------data for the part table -------------------------------
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'AT94', N'Iron', 44, N'HW', 3, 23.7025)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'BV06', N'Home Gym', 45, N'SG', 2, 755.2025)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'CD52', N'Microwave Oven', 32, N'AP', 1, 156.7500)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'DL71', N'Cordless Drill', 21, N'HW', 3, 123.4525)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'DR93', N'Gas Range', 10, N'AP', 2, 470.2500)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'DW11', N'Washer', 9, N'AP', 3, 379.9905)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'FD21', N'Stand Mixer', 22, N'HW', 3, 151.9525)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'KL62', N'Dryer', 12, N'AP', 1, 332.4525)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'KT03', N'Dishwasher', 8, N'AP', 3, 565.2500)
INSERT [dbo].[PART] ([PART_NUM], [PART_DESCRIPTION], [UNITS_ON_HAND], [CATEGORY], [WAREHOUSE], [PRICE]) VALUES (N'KV29', N'Treadmill', 7, N'SG', 2, 1320.5000)
go
-----------data for customer table--------------------
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (148 , N'AI''s Appliance and Sport', N'2837 Greenway', N'Fillmore', N'FL', N'33336', 0.0000, 7500, N'20', NULL, '2015-10-15')
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (282, N'Brookings Direct', N'3827 Devon', N'Grove', N'FL', N'33321', 0.0, 10000, N'35', NULL, '2015-10-10')
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (356, N'Ferguson''s', N'382 Wildwood', N'Northfield', N'FL', N'33146', 0.0000, 7500, N'65', NULL, '2015-10-10')
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (408, N'The Everything Shop', N'1828 Raven', N'Crystal', N'FL', N'33503', 0.0000, 5000, N'35', NULL, '2015-10-10')
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (462, N'Bargains Galore', N'3829 Central', N'Grove', N'FL', N'33321', 0.0000, 10000, N'65', NULL, '2015-10-10')
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (524, N'Kline''s', N'838 Ridgeland', N'Fillmore', N'FL', N'33336', 0.0000, 15000, N'20', NULL, getdate())
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (608, N'Johnson''s Department Store', N'372 Oxford', N'Sheldon', N'FL', N'33553', 0.0000, 10000, N'65', NULL, '2015-10-10')
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (687, N'Lee''s Sport and Appliance', N'282 Evergreen', N'Altonville', N'FL', N'32543', 0.0000, 5000, N'35', NULL, getdate())
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (725, N'Deerfield''s Four Seasons', N'282 Columbia', N'Sheldon', N'FL', N'33553', 0.0000, 7500, N'35', NULL, getdate())
INSERT [dbo].[CUSTOMER] ([CUST_NUM], [CUST_NAME], [CUST_STREET], [CUST_CITY], [CUST_STATE], [CUST_ZIP], [CUST_BALANCE], [CREDIT_LIMIT], [REP_NUM], [PHONE], [BEGIN_DATE]) VALUES (842, N'All Season', N'28 Lakeview', N'Grove', N'FL', N'33321', 0.0000, 7500, N'20', NULL, getdate())
--------------data for orders table------------------------- 
set identity_insert orders on 
go
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21600, '2015-10-20', 148)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21601, '2015-10-20', 148)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21602, '2015-10-21', 356)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21603, '2015-10-21', 408)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21604, '2015-10-23', 282)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21605, '2015-11-15', 608)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21606, '2015-11-20', 148)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21607, '2015-12-21', 608)
insert [dbo].[orders] (order_num,order_date,cust_num) values (21608, '2015-12-21', 608)
insert [dbo].[orders](order_num,order_date, cust_num) values (21609,'2016-01-08', 356)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21610, '2016-01-10', 408)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21611, '2016-01-10', 148)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21612, '2016-01-15', 148)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21613, '2016-01-22', 408)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21614, '2016-02-03', 148)
INSERT [dbo].[ORDERS] ([ORDER_NUM], [ORDER_DATE], [CUST_NUM]) VALUES (21615, '2016-02-03', 462)
go
insert into orders (order_num,order_date,cust_num)  values (21616, '2018-12-23', 356) 
go
insert into orders (order_num,order_date,cust_num) values (21617, '2018-12-23', 462) 
go
insert into orders (order_num,order_date,cust_num) values (21618, '2018-12-23', 608)
  go 
 
set identity_insert orders off        
begin transaction
begin try
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21600, N'DR93', 3, 495.0000, NULL) 
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21600, N'KV29', 2, 1290.0000, NULL)
   commit
end try 
begin catch ;
   print 'encountered problem';
   rollback transaction;
   throw;
end catch

go

INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21601, N'AT94', 11, 21.9500, NULL)
go
begin transaction
begin try
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21602, N'DR93', 1, 495.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21602, N'DW11', 1, 399.9900, NULL)
  commit
end try
begin catch 
    rollback transaction;
	throw
end catch

go
begin transaction 
begin try
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21603, N'KL62', 4, 329.9500, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21604, N'KT03', 2, 595.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21605, N'BV06', 2, 794.9500, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21605, N'CD52', 4, 150.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21606, N'AT94', 1, 22.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21606, N'DR93', 1, 495.0000, NULL)
commit
end try
begin catch
   rollback transaction;
   throw;
end catch

go

begin transaction
begin try

INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21607, N'KV29', 2, 1290.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21608, N'AT94', 3, 25.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21609, N'DR93', 1, 450.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21609, N'DW11', 1, 400.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21610, N'AT94', 1, 24.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21610, N'DW11', 1, 379.0000, NULL)
commit
end try
begin catch
   rollback transaction;
   throw;
end catch

go

begin transaction
begin try

INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21611, N'CD52', 1, 156.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21611, N'FD21', 1, 150.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21612, N'KL62', 1, 330.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21612, N'KT03', 1, 565.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21613, N'DR93', 1, 470.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21613, N'DW11', 1, 370.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21614, N'DW11', 1, 370.0000, NULL)
INSERT [dbo].[ORDER_LINE] ([ORDER_NUM], [PART_NUM], [QTY_ORDERED], [QUOTED_PRICE],  [Modified_Date]) VALUES (21614, N'KL62', 1, 330.0000, NULL)
commit
end try
begin catch
   rollback transaction;
   throw
end catch

go


begin transaction
begin try

insert into order_line(order_num, part_num, qty_ordered,quoted_price)
  values (21615, 'KL62' ,1,330.00),
         (21615, 'KT03' ,1,565.00)
 
 insert into order_line(order_num, part_num, qty_ordered,
quoted_price)  values (21616, 'BV06', 1, 755.00)
 
 insert into order_line(order_num, part_num, qty_ordered,
quoted_price)
  values (21617, 'BV06',1, 755.00)
  
  insert into order_line(order_num, part_num, qty_ordered,
quoted_price)
  values (21618, 'BV06',1, 755.00)
  commit
end try
begin catch
   rollback transaction;
   throw;
end catch

 
begin transaction
begin try
insert into orders(order_date, cust_num) values ( getdate(), 282)

--find the last value that was assigned
declare @ordernum int
select @ordernum = max(order_num) from orders
insert into order_line(order_num,part_num,qty_ordered,quoted_price)
 values(@ordernum, 'BV06', 1 ,750.00)
commit
end try
begin catch
   rollback transaction;
   throw;
end catch

go
begin transaction
begin try
insert into orders(order_date, cust_num) values (getdate(),282)

declare @orderno int
select @orderno = max(order_num) from orders

insert into order_line (order_num , part_num ,qty_ordered,quoted_price)
  values (@orderno, 'DW11', 1,380.00)
commit
end try
begin catch
    rollback transaction;
	throw;
end catch
go
begin transaction
begin try

insert into orders(order_date,cust_num)  values (getdate(),282)
declare @orderNUM int
select @orderNUM = max(order_num) from orders
insert into order_line (order_num, part_num ,qty_ordered,quoted_price)
values (@orderNUM, 'KV29', 1 , 1300.00)
commit
end try
begin catch
  rollback transaction;
  throw;
end catch
go

select @@trancount   --should be zero , all transactions were processed
select * from employee
select * from salesrep
select * from customer
select * from orders
select * from order_line
select * from part

--test if an entire transaction is undone if something goes wrong
-- exceed the amount of inventory
--  have a customer order 4 gas ranges at 440.00 each , there are only three in inventory
select * from part  --DR93 only three left
select * from customer where cust_num = 282
select * from salesrep where rep_num = 35
select * from orders where cust_num = 282 
select * from order_line where order_num = 21619  --order_num 21619
begin transaction
begin try
  insert into order_line (order_num,part_num, qty_ordered,quoted_price)
    values(21619,'DR93',4,440.00)
  commit
end try
begin catch
   rollback transaction;
   throw;
end catch

go
select @@trancount

--TEST IF SALESREP 35 COMMISSION INCREASES
begin transaction
begin try
INSERT INTO order_LINE   --ADD ANOTHER PART TO THE SAME ORDER
  VALUES(21613,'CD52', 1,100.00 ,  '2015-10-22')
commit
end try
begin catch
  rollback transaction;
  throw;
end catch
go

select * from order_line where order_num = 21613
select * from orders where order_num = 21613
select * from customer where cust_num = 408
select * from salesrep where rep_num = 35
--MODIFY AN ORDER AND VERIFY THAT THE COMMISSION , CUSTOMER RECORD AND PART RECORD CHANGES ACCORDINGLY
begin transaction
begin try
UPDATE ORDER_LINE
  SET QTY_ORDERED = 1 
  WHERE ORDER_NUM = 21613 AND PART_NUM = 'DW11'
commit
end try
begin catch 
  rollback transaction;
  throw
end catch
go


-- REMOVE PART OF AN ORDER AND VERIFY THAT COMMISSION, CUSTOMER RECORD AND PART RECORD CHANGES ACCORDINGLY
begin transaction
begin try
DELETE FROM ORDER_LINE
WHERE ORDER_NUM = 21614 AND PART_NUM = 'KL62'

commit
end try
begin catch 
   rollback transaction;
   throw;
end catch
go
use PremiereCo
go

select * from employee
select * from salesrep
select * from customer
select * from orders
select * from order_line
select * from part




 
-- create  a sql server authenticated login 
--use master 
--go
--create login premiereLogin with password = 'premiereL0g!n'
----create a database user
--use premiereCo
--go
--create user premiereUser from login  premiereLogin
----assign roles to the new user
--alter  role db_datareader add member premiereUser
--alter  role db_datawriter add member premiereUser

