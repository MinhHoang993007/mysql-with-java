package muc;

import java.util.Scanner;
import java.sql.*;

//my package
import db_choose.*;
import check_exists.*;
import menu.*;
import sqlPerforment.*;

public class QuanLyLop{

    Statement stmt;
    public QuanLyLop(Statement stmt){
	this.stmt=stmt;
    }
    
    public void exec() throws  SQLException, Exception{
	String sql=new String();
	String MaLop=new String();
	String MaMH=new String();
	String NamHoc=new String();
	int HocKy;
	String MaGV=new String();
	String MaSV=new String();
	    
	String check=new String();
	Scanner scan=new Scanner(System.in);

	String[] fieldsNameToPrint;

	System.out.println();
	int choice;
	do
	    {
		choice=new Menu().getChoice(Menu.menuQuanLyLop);
		switch(choice)
		    {
		    case 1:
			System.out.println("Tạo lớp mới");
			System.out.println();
			    
			do{
			    try{
				System.out.print("Ma lop: ");
				MaLop=scan.nextLine();
				
				if(new CheckExists(stmt).checkExists("Lop","MaLop='"+MaLop+"'")==1){
				    System.out.println();
				    System.out.println("The record already exists");
				}else{
				    System.out.print("Ma mon hoc: ");
				    MaMH=scan.nextLine();
				    
				    //check if exists
				    if(new CheckExists(stmt).checkExists("MonHoc","MaMH='"+MaMH+"'")==0){
					System.out.println("Mon Hoc doesn't exists");
				    }
				    else {
					System.out.print("Ma giao vien: ");
					MaGV=scan.nextLine();
					
					//check if exists
					if(new CheckExists(stmt).checkExists("GiaoVien","MaGV='"+MaGV+"'")==0){
					    System.out.println();
					    System.out.println("The teacher doesn't exits");	
					}
					else {
					    System.out.print("Nam hoc (xxxx-xxxx): ");
					    NamHoc=scan.nextLine();
					    
					    System.out.print("Hoc ky: ");
					    HocKy=scan.nextInt();
					    check=scan.nextLine();//CLEAR BUFFER
					    
					    sql="insert into Lop "
						+"values('"+MaLop+"','"+MaMH+"','"+NamHoc+"',"+HocKy+",'"+MaGV+"')";
					    try{
						stmt.executeUpdate(sql);
					    }catch(SQLException se){
						se.printStackTrace();scan.nextLine();
					    }
					}
				    }
				}
			    }catch(Exception e){
				e.printStackTrace();scan.nextLine();
			    }
			    System.out.print("\n### Type y to continue/n to exit: ");
			}while(scan.nextLine().equals("y"));
			break;
			    
		    case 2:
			System.out.println("Sửa đổi thông tin lớp\n");
			    
			do{
			    try{
				System.out.print("Ma lop need to be updated: ");
				String UDMaLop=scan.nextLine();
				
				//check if exists
				if(new CheckExists(stmt).checkExists("Lop","MaLop='"+UDMaLop+"'")==0){
				    System.out.println("The record doesn't exitst\n");
				}
				else{//exitst
				    sql="select * from Lop where MaLop='"+UDMaLop+"'";
				
				    fieldsNameToPrint=new String[]{"MaLop","MaMH","NamHoc","HocKy","MaGV"};
				    new SQLPerforment(stmt).prtQueryRs(sql,fieldsNameToPrint,fieldsNameToPrint);

				    //start to update
				    System.out.print("\nUpdate ma lop? (y/n): ");
				    check=scan.nextLine();

				    if(check.equals("y"))
					{
					    System.out.print("Ma lop: ");
					    MaLop=scan.nextLine();
					    
					    if(new CheckExists(stmt).checkExists("Lop","MaLop='"+MaLop+"'")==1){
						System.out.println("The record already exists");
						System.out.println();
					    }
					    else{//exitst
						sql="update Lop set MaLop='"+MaLop+"'  where MaLop='"+UDMaLop+"'";//notice the space " where
						try{
						    stmt.executeUpdate(sql);
						}catch(SQLException se){
						    se.printStackTrace();scan.nextLine();
						}
						UDMaLop=MaLop;
					    }
					}
				    
				    System.out.print("\nUpdate ma mon hoc? (y/n): ");
				    check=scan.nextLine();

				    if(check.equals("y"))
					{
					    System.out.print("Ma mon hoc: ");
					    MaMH=scan.nextLine();
					    
					    if(new CheckExists(stmt).checkExists("MonHoc","MaMH='"+MaMH+"'")==0){
						System.out.println("Mon Hoc doesn't exists");
					    }
					    else {
						sql="update Lop set MaMH='"+MaMH+"' where MaLop='"+UDMaLop+"'";
						try{
						    stmt.executeUpdate(sql);
						}catch(SQLException se){
						    se.printStackTrace();scan.nextLine();
						}
					    }
					}
				    
				    System.out.print("\nUpdate nam hoc? (y/n): ");
				    check=scan.nextLine();

				    if(check.equals("y"))
					{
					    System.out.print("Nam hoc (xxxx-xxxx): ");
					    NamHoc=scan.nextLine();
					    sql="update Lop set NamHoc='"+NamHoc+"' where MaLop='"+UDMaLop+"'";
					    try{
						stmt.executeUpdate(sql);
					    }catch(SQLException se){
						se.printStackTrace();scan.nextLine();
					    }
					}
				    
				    System.out.print("\nUpdate hoc ky? (y/n): ");
				    check=scan.nextLine();

				    if(check.equals("y"))
					{
					    System.out.print("Hoc ky: ");
					    HocKy=scan.nextInt();
					    check=scan.nextLine();//CLEAR BUFFER
					    sql="update Lop set HocKy="+HocKy+" where MaLop='"+UDMaLop+"'";
					    try{
						stmt.executeUpdate(sql);
					    }catch(SQLException se){
						se.printStackTrace();scan.nextLine();
					    }
					}
				    
				    System.out.print("\nUpdate giao vien? (y/n): ");
				    check=scan.nextLine();

				    if(check.equals("y"))
					{
					    System.out.print("Ma giao vien: ");
					    MaGV=scan.nextLine();
					    
					    if(new CheckExists(stmt).checkExists("GiaoVien","MaGV='"+MaGV+"'")==0){
						System.out.println("\nThe teacher doesn't exits");	
					    }
					    else {
						sql="update Lop set MaGV='"+MaGV+"'  where MaLop='"+UDMaLop+"'";//notice the space " where
						try{
						    stmt.executeUpdate(sql);
						}catch(SQLException se){
						    se.printStackTrace();scan.nextLine();
						}
					    }
					}
				    
				    sql="select * from Lop where MaLop='"+UDMaLop+"'";
				    new SQLPerforment(stmt).prtQueryRs(sql,fieldsNameToPrint,fieldsNameToPrint);
				}
			    }catch(Exception e){
				e.printStackTrace();scan.nextLine();
			    }
			    System.out.print("\n### Type y to continue/n to exit: ");
			}while(scan.nextLine().equals("y"));
			break;
			    
		    case 3:
			System.out.println("\nBổ sung sinh viên vào lớp\n");
			    
			String ISClass=new String();//INSERT CLASS
			do{
			    try{
				System.out.print("Input the MaLop you want to add student to: ");
				ISClass=scan.nextLine();
				
				//check if exists
				if(new CheckExists(stmt).checkExists("Lop","MaLop='"+ISClass+"'")==0){
				    System.out.println("The class doesn't exists");
				}
				else {
				    System.out.println();
				    System.out.println("Class: "+ISClass+":");
				    System.out.println();
				    String check2=new String();
				    do
					{
					    System.out.print("Ma sinh vien: ");
					    MaSV=scan.nextLine();
					    
					    //check if exists
					    if(new CheckExists(stmt).checkExists("SinhVien","MaSV='"+MaSV+"'")==0){
						System.out.println("The SinhVien doesn't exists");
					    }
					    else
						{
						    //check if exists
						    if(new CheckExists(stmt).checkExists("SinhVienLop","MaSV='"+MaSV+"' and "+"MaLop='"+ISClass+"'")==1){
							System.out.println("\nThe record already exists");
							
						    }
						    else {
							sql="insert into SinhVienLop (MaSV,MaLop)"
							    +"values('"+MaSV+"','"+ISClass+"')";
							try{
							    stmt.executeUpdate(sql);
							}catch(SQLException se){
							    se.printStackTrace();scan.nextLine();
							}
						    }
						}
					    
					    System.out.print("\nDo you want to continue insert studen? (y/n): ");
					    check2=scan.nextLine();
					    System.out.println();
					}while(check2.equals("y"));
				}
			    }catch(Exception e){
				e.printStackTrace();scan.nextLine();
			    }
			    System.out.print("\nDo you want to continue with new class? (y/n): ");
			    check=scan.nextLine();
			    System.out.println();
			}while(check.equals("y"));
			    
			break;
			    
		    case 4:
			System.out.println("Loại bỏ sinh viên khỏi lớp\n");
			    
			String DLClass=new String();//DELETE CLASS
			do{
			    try{
				System.out.print("Input the MaLop you want to delete student from: ");
				DLClass=scan.nextLine();
				
				//check if exists
				if(new CheckExists(stmt).checkExists("Lop","MaLop='"+DLClass+"'")==0){
				    System.out.println("The class doesn't exists");
				}
				else {
				    System.out.println();
				    System.out.println("Class: "+DLClass+":");
				    System.out.println();
				    String check2=new String();
				    do
					{
					    System.out.print("Ma sinh vien want to delete: ");
					    MaSV=scan.nextLine();
					    
					    //check if exists
					    if(new CheckExists(stmt).checkExists("SinhVienLop","MaSV='"+MaSV+"' and "+"MaLop='"+DLClass+"'")==0){
						System.out.println("\nThe SinhVien doesn't exists in this class");
					    }
					    else
						{
						    sql="delete from SinhVienLop where MaSV='"+MaSV+"' and "+"MaLop='"+MaLop+"'";
						    try{
							stmt.executeUpdate(sql);
						    }catch(SQLException se){
							se.printStackTrace();scan.nextLine();
						    }
						}
					    
					    System.out.print("\nDo you want to continue delete studen from this class? (y/n): ");
					    check2=scan.nextLine();
					    System.out.println();
					}while(check2.equals("y"));
				}
			    }catch(Exception e){
				e.printStackTrace();scan.nextLine();
			    }
			    
			    System.out.print("\nDo you want to continue with new class? (y/n): ");
			    check=scan.nextLine();
			    System.out.println();
			}while(check.equals("y"));
			break;
			    
		    case 5:
			System.out.println("Hủy lớp\n");
			    
			do{
			    try{
				System.out.print("Input the MaLop you want to delete: ");
				MaLop=scan.nextLine();
				
				//check if exists
				if(new CheckExists(stmt).checkExists("Lop","MaLop='"+MaLop+"'")==0){
				    System.out.println("The class doesn't exists");
				}
				else {    
				    sql="delete from Lop where MaLop='"+MaLop+"'";
				    try{
					stmt.executeUpdate(sql);
				    }catch(SQLException se){
					se.printStackTrace();scan.nextLine();
				    }	   
				}
			    }catch(Exception e){
				e.printStackTrace();scan.nextLine();
			    }
			    
			    System.out.print("\nDo you want to continue with new class? (y/n): ");
			    check=scan.nextLine();
			    System.out.println();
			}while(check.equals("y"));
			break;
			    
		    case 6:
			System.out.println("In danh sách lớp");
			System.out.println();
			    
			sql="select * from Lop";
			    
			fieldsNameToPrint=new String[]{"MaLop","MaMH","NamHoc","HocKy","MaGV"};
			new SQLPerforment(stmt).prtQueryRs(sql,fieldsNameToPrint,fieldsNameToPrint);
			scan.nextLine();
			break;


		    case 7:
			System.out.println("Quit");
			System.out.println("**************************************");
			System.out.println();
			break;
		    }
	    }while(choice!=7);
//scan.close();
    }
}
