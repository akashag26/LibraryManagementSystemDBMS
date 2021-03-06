package com.dbms.queries;

public final class SqlQueries {
	
	public static final String GETALLBOOKS = "SELECT BOOK.ECOPY,BOOK.ISBN,BOOK.TITLE,BOOK.EDITION,BOOK.YEAR,BOOK.PUBLISHER,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY,BOOK.RESOURCEID FROM BOOK,LIBRARY,LIBRARYCATEGORY WHERE BOOK.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND BOOK.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID";
	public static final String GETBOOKAUTHORS = "SELECT AUTHOR.AUTHORID,AUTHOR.AUTHORNAME FROM AUTHOR,BOOKAUTHOR WHERE AUTHOR.AUTHORID = BOOKAUTHOR.AUTHORID AND BOOKAUTHOR.ISBN = ?";
	public static final String GETCAMERAS = "SELECT * FROM CAMERA";
	public static final String GETCONFERENCEPAPERS = "SELECT CONFERENCE.*,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY FROM CONFERENCE, LIBRARY, LIBRARYCATEGORY WHERE CONFERENCE.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CONFERENCE.CATEGORYID =LIBRARYCATEGORY.CATEGORYID AND LIBRARYCATEGORY.LIBRARYID = LIBRARY.LIBRARYID";
	public static final String GETCONFERENCEAUTHORS = "SELECT AUTHOR.AUTHORID,AUTHOR.AUTHORNAME from CONFERENCEAUTHOR, CONFERENCE, AUTHOR where CONFERENCE.CONFERENCENUMBER = CONFERENCEAUTHOR.CONFERENCENUMBER AND CONFERENCEAUTHOR.AUTHORID = AUTHOR.AUTHORID AND CONFERENCE.CONFERENCENUMBER = ?";
	public static final String LOGIN = "Select StudentNumber From LoginInfoStudent where Username= ? and PASSWORD= ? ";
	public static final String STUDENT_DETAILS = "select StudentNumber,FName,LName,PhoneNumber,AlternatePhone,Street,City,PostCode,DOB,Sex,Nationality,Department,Classification,DegreeProgram,Year,State from Student,Programme where student.ProgramId=Programme.ProgramId and Student.StudentNumber= ? ";
	public static final String LOGINFACULTY = "Select FacultyNumber From LoginInfoFaculty where Username= ? and PASSWORD= ? ";
	
	public static final String GETALLJOURNALS = "SELECT * FROM JOURNAL,LIBRARY,LIBRARYCATEGORY WHERE JOURNAL.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND JOURNAL.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID";
	public static final String GETJOURNALAUTHORS = "SELECT * FROM JOURNALAUTHOR,AUTHOR WHERE JOURNALAUTHOR.AUTHORID=AUTHOR.AUTHORID AND JOURNALAUTHOR.ISSN = ?";
	public static final String GETALLROOMS = "SELECT * FROM ROOM,LIBRARY,LIBRARYCATEGORY WHERE ROOM.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID";
	public static final String GETPROGRAMME="SELECT CLASSIFICATION,DEGREEPROGRAM,YEAR,programid FROM PROGRAMME";
	public static final String GETCOURSEBOOK="SELECT COURSENAME FROM BOOKRESERVED WHERE ISBN=?";
	public static final String ISCOURSETAKEN="select coursename from coursestaken where studentnumber=?";
	public static final String BOOKAVAILABILITY="select availablecopies from book where isbn=?";
	public static final String CHECKOUT="insert into checkout values(?,?,?,?,?,?,?,?)";
	public static final String UPDATEBOOKCOUNTER="update book set availablecopies=availablecopies-1 where isbn=?";
	public static final String BOOKWAIT="insert into checkoutwait values(?,?,?,?,?,?,?)";
	public static final String UPDATESTUDENT="update student set Fname=? , Lname=?, Phonenumber=?, ALTERNATEPHONE=?,Street=?, City=?, Postcode=?, DOB=TO_DATE(?,'YYYY-MM-DD'),Sex=?, nationality=?, Department=?, Programid=?,State=? where studentnumber=?";
	public static final String BOOKALREADYISSUEDFORSTUDENT="select book.isbn, book.availablecopies from book, checkout where book.resourceid=checkout.resourceid and checkout.resourceid=? and checkout.STUDENTNUMBER=? and book.isbn=?";
	public static final String BOOKALREADYISSUEDFORFACULTY="select book.isbn, book.availablecopies from book, checkout where book.resourceid=checkout.resourceid and checkout.resourceid=? and checkout.FACULTYNUMBER=? and book.isbn=?";
	public static final String ISBOOKRENEWABLE="select * from checkoutwait where resourceid=?";
	public static final String UPDATECONFERENCECOUNTER="update CONFERENCE set availablecopies=availablecopies-1 where CONFERENCENUMBER=?";
	public static final String CONFERENCEALREADYISSUEDFORSTUDENT="select conference.conferencenumber, conference.availablecopies from conference, checkout where conference.resourceid=checkout.resourceid and checkout.resourceid=? and checkout.STUDENTNUMBER=? and conference.conferencenumber=?";
	public static final String CONFERENCEALREADYISSUEDFORFACULTY="select conference.conferencenumber, conference.availablecopies from conference, checkout where conference.resourceid=checkout.resourceid and checkout.resourceid=? and checkout.FACULTYNUMBER=? and conference.conferencenumber=?";
	public static final String CONFERENCEAVAILABILITY="select availablecopies from conference where conferencenumber=?";
	public static final String CONFERENCEWAIT="insert into checkoutwait values(?,?,?,?,?,?,?)";
	public static final String CHECKINCHECKOUTWAITSTUDENT = "select * from CHECKOUTWAIT WHERE RESOURCEID = ? AND STUDENTNUMBER = ?";
	public static final String CHECKINCHECKOUTWAITFACULTY = "select * from CHECKOUTWAIT WHERE RESOURCEID = ? AND FACULTYNUMBER = ?";
	public static final String ISCONFERENCERENEWABLE="select * from checkoutwait where resourceid=?";
	
	public static final String UPDATEJOURNALCOUNTER="update journal set availablecopies=availablecopies-1 where issn=?";
	public static final String JOURNALSREADYISSUEDFORSTUDENT="select journal.issn, journal.availablecopies from journal, checkout where journal.resourceid=checkout.resourceid and checkout.resourceid=? and checkout.STUDENTNUMBER=? and journal.issn=?";
	public static final String JOURNALSREADYISSUEDFORFACULTY="select journal.issn, journal.availablecopies from journal, checkout where journal.resourceid=checkout.resourceid and checkout.resourceid=? and checkout.FACULTYNUMBER=? and journal.issn=?";
	public static final String JOURNALAVAILABILITY="select availablecopies from journal where issn=?";
	public static final String JOURNALWAIT="insert into checkoutwait values(?,?,?,?,?,?,?)";
	public static final String ISJOURNALRENEWABLE="select * from checkoutwait where resourceid=?";
	
	public static final String CHECKOUTUPDATEFORSTUDENT="update checkout set checkouttime=?, reservedupto=? where resourceid=? and studentnumber=?";
	public static final String CHECKOUTUPDATEFORFACULTY="update checkout set checkouttime=?, reservedupto=? where resourceid=? and facultynumber=?";
	
	public static final String GETDISTINCTROOMCAPCITY= "SELECT DISTINCT CAPACITY FROM ROOM";
	public static final String GETLIBRARYNAMES= "SELECT * FROM LIBRARY";
	//public static final String SEARCHROOMS = "SELECT * FROM ROOM,LIBRARY,LIBRARYCATEGORY WHERE CAPACITY=? AND ROOM.LIBRARYID = LIBRARY.LIBRARYID AND LIBRARY.LIBRARYNAME = ? AND LIBRARYCATEGORY.CATEGORYID = ROOM.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID";
	public static final String SEARCHROOMS = 
			"SELECT ROOM.*,LIBRARY.*,LIBRARYCATEGORY.* FROM ROOM,LIBRARY,LIBRARYCATEGORY "
			+ "WHERE CAPACITY=? AND ROOM.LIBRARYID = LIBRARY.LIBRARYID AND LIBRARY.LIBRARYNAME = ? "
			+ "AND LIBRARYCATEGORY.CATEGORYID = ROOM.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID "
			+ "AND ROOM.ROOMID "
			+ "NOT IN "
			+ "(SELECT ROOMID FROM ROOMRESERVATION WHERE ? >= ROOMRESERVATION.STARTTIME AND ? < ROOMRESERVATION.ENDTIME "
			+ "UNION "
			+ "SELECT ROOMID FROM ROOMRESERVATION WHERE ? > ROOMRESERVATION.STARTTIME AND ? <= ROOMRESERVATION.ENDTIME "
			+ "UNION "
			+ "SELECT ROOMID FROM ROOMRESERVATION WHERE ? <= ROOMRESERVATION.STARTTIME AND  ? >= ROOMRESERVATION.ENDTIME)";
	
	public static final String MAKEROOMRESERVATION = "INSERT INTO ROOMRESERVATION VALUES(?, ? , ? ,? , ?, ?)";
	public static final String GETROOMRESERVATIONID = "SELECT MAX(RESERVATIONID)+1 as NEWID FROM ROOMRESERVATION";
	public static final String BOOKCAMERA="insert into camerareservation values(?,?,?,?,?,?)";
	public static final String GETCAMERARESERVATION="select * from camerareservation where cameraid=?";
	public static final String GETCAMERARESERVATION1="select max(endtime), min(starttime) from camerareservation where cameraid=?";
	public static final String GETCAMERARESERVATIONID = "SELECT MAX(RESERVATIONID)+1 as NEWID FROM CAMERARESERVATION";
	public static final String GETMAXCAMERAQUEUE = "SELECT MAX(QUEUENO) FROM CAMERAQUEUE WHERE CAMERAID=?";
	public static final String CHECKCAMERAQUEUEFORSTUDENT="select * from cameraqueue where cameraid=? and studentnumber=?";
	public static final String CHECKCAMERAQUEUEFORFACULTY="select * from cameraqueue where cameraid=? and facultynumber=?";
	public static final String CHECKCAMERARESERVATIONFORSTUDENT="select * from camerareservation where cameraid=? and studentnumber=?";
	public static final String CHECKCAMERARESERVATIONFORFACULTY="select * from camerareservation where cameraid=? and facultynumber=?";
	public static final String CAMERAQUEUE="insert into cameraqueue values(?,?,?,?,?,?)";
	public static final String GETRESERVEDROOMSBYSTUDENT = "SELECT * FROM ROOM,ROOMRESERVATION,LIBRARY,LIBRARYCATEGORY WHERE ROOM.ROOMID=ROOMRESERVATION.ROOMID AND ROOMRESERVATION.RESERVATIONID NOT IN (SELECT RESERVATIONID FROM CHECKOUTROOM) AND ROOM.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOMRESERVATION.STUDENTNUMBER=?";
	public static final String GETRESERVEDROOMSBYFACULTY = "SELECT * FROM ROOM,ROOMRESERVATION,LIBRARY,LIBRARYCATEGORY WHERE ROOM.ROOMID=ROOMRESERVATION.ROOMID AND ROOMRESERVATION.RESERVATIONID NOT IN (SELECT RESERVATIONID FROM CHECKOUTROOM) AND ROOM.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOMRESERVATION.FACULTYNUMBER=?";
	public static final String GETCAMERASRESERVEDBYSTUDENT = "SELECT * FROM CAMERA,CAMERARESERVATION,LIBRARY,LIBRARYCATEGORY WHERE CAMERA.CAMERAID=CAMERARESERVATION.CAMERAID AND CAMERARESERVATION.RESERVATIONID NOT IN (SELECT RESERVATIONID FROM CAMERACHECKOUT) AND CAMERA.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CAMERA.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CAMERARESERVATION.STUDENTNUMBER=?";
	public static final String GETCAMERASRESERVEDBYFACULTY = "SELECT * FROM CAMERA,CAMERARESERVATION,LIBRARY,LIBRARYCATEGORY WHERE CAMERA.CAMERAID=CAMERARESERVATION.CAMERAID AND CAMERARESERVATION.RESERVATIONID NOT IN (SELECT RESERVATIONID FROM CAMERACHECKOUT) AND CAMERA.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CAMERA.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CAMERARESERVATION.FACULTYNUMBER=?";
	public static final String CHECKOUTROOM = "INSERT INTO CHECKOUTROOM VALUES(?,?,?)";
	public static final String CHECKINROOM = "UPDATE CHECKOUTROOM SET CHECKINTIME = ? WHERE RESERVATIONID = ?";
	public static final String CHECKOUTCAMERA = "INSERT INTO CAMERACHECKOUT VALUES(?,?,?)";
	public static final String CHECKINCAMERA = "UPDATE CAMERACHECKOUT SET CHECKINTIME = ? WHERE RESERVATIONID = ?";
	public static final String CHECKINPUBLICATIONSFORSTUDENT = "UPDATE CHECKOUT SET CHECKINTIME = ? WHERE RESOURCEID = ? AND STUDENTNUMBER=?";
	public static final String CHECKINPUBLICATIONSFORFACULTY = "UPDATE CHECKOUT SET CHECKINTIME = ? WHERE RESOURCEID = ? AND FACULTYNUMBER=?";
	public static final String GETALLCHECKOUTBOOKSBYSTUDENTID = "SELECT BOOK.ISBN,BOOK.TITLE,BOOK.EDITION,BOOK.YEAR,BOOK.PUBLISHER,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY,BOOK.RESOURCEID FROM BOOK,LIBRARY,LIBRARYCATEGORY,CHECKOUT WHERE BOOK.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND BOOK.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CHECKOUT.RESOURCEID=BOOK.RESOURCEID AND CHECKOUT.STUDENTNUMBER= ? AND CHECKINTIME is null";
	public static final String GETALLCHECKOUTBOOKSBYFACULTYID = "SELECT BOOK.ISBN,BOOK.TITLE,BOOK.EDITION,BOOK.YEAR,BOOK.PUBLISHER,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY,BOOK.RESOURCEID FROM BOOK,LIBRARY,LIBRARYCATEGORY,CHECKOUT WHERE BOOK.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND BOOK.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CHECKOUT.RESOURCEID=BOOK.RESOURCEID AND CHECKOUT.FACULTYNUMBER= ? AND CHECKINTIME is null";
	public static final String GETALLCHECKOUTROOMSBYSTUDENTID = "SELECT * FROM ROOM,LIBRARY,LIBRARYCATEGORY,CHECKOUTROOM,ROOMRESERVATION WHERE CHECKOUTROOM.RESERVATIONID=ROOMRESERVATION.RESERVATIONID AND ROOMRESERVATION.STUDENTNUMBER= ? AND ROOM.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.ROOMID = ROOMRESERVATION.ROOMID AND CHECKINTIME is null";
	public static final String GETALLCHECKOUTROOMSBYFACULTYID = "SELECT * FROM ROOM,LIBRARY,LIBRARYCATEGORY,CHECKOUTROOM,ROOMRESERVATION WHERE CHECKOUTROOM.RESERVATIONID=ROOMRESERVATION.RESERVATIONID AND ROOMRESERVATION.FACULTYNUMBER= ? AND ROOM.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND ROOM.ROOMID = ROOMRESERVATION.ROOMID AND CHECKINTIME is null";
	public static final String GETALLCHECKOUTCAMERASBYSTUDENTID = "SELECT * FROM CAMERA,CAMERARESERVATION,CAMERACHECKOUT WHERE CAMERA.CAMERAID=CAMERARESERVATION.CAMERAID AND CAMERARESERVATION.RESERVATIONID = CAMERACHECKOUT.RESERVATIONID AND CAMERARESERVATION.STUDENTNUMBER=? AND CHECKINTIME is null";
	public static final String GETALLCHECKOUTCAMERASBYFACULTYID = "SELECT * FROM CAMERA,CAMERARESERVATION,CAMERACHECKOUT WHERE CAMERA.CAMERAID=CAMERARESERVATION.CAMERAID AND CAMERARESERVATION.RESERVATIONID = CAMERACHECKOUT.RESERVATIONID AND CAMERARESERVATION.FACULTYNUMBER=? AND CHECKINTIME is null";
	public static final String GETALLREMINDERSFORSTUDENTS= "SELECT * FROM REMINDER,STUDENT WHERE REMINDER.STUDENTNUMBER=STUDENT.STUDENTNUMBER AND STUDENT.STUDENTNUMBER=?";
	public static final String GETALLREMINDERSFORFACULTY= "SELECT * FROM REMINDER,FACULTY WHERE REMINDER.FACULTYNUMBER=FACULTY.FACULTYNUMBER AND FACULTY.FACULTYNUMBER=?";
	public static final String UPDATENUMBEROFBOOKCOPIES = "UPDATE BOOK SET AVAILABLECOPIES=AVAILABLECOPIES+1 WHERE ISBN = ?";
	public static final String UPDATENUMBEROFJOURNALCOPIES = "UPDATE JOURNAL SET AVAILABLECOPIES=AVAILABLECOPIES+1 WHERE ISSN = ?";
	public static final String UPDATENUMBEROFCONFERENCECOPIES = "UPDATE CONFERENCE SET AVAILABLECOPIES=AVAILABLECOPIES+1 WHERE CONFERENCENUMBER = ?";
	public static final String DELETEFROMPUBLICATIONCHECKOUTSTUDENT ="DELETE FROM CHECKOUT WHERE CHECKOUT.STUDENTNUMBER= ? AND CHECKINTIME IS NOT null AND CHECKOUT.RESOURCEID = ?";
	public static final String DELETEFROMPUBLICATIONCHECKOUTFACULTY ="DELETE FROM CHECKOUT WHERE CHECKOUT.FACULTYNUMBER= ? AND CHECKINTIME IS NOT null AND CHECKOUT.RESOURCEID = ?";
	public static final String DELETEFROMROOMRESERVESTUDENT ="DELETE FROM ROOMRESERVATION WHERE ROOMRESERVATION.STUDENTNUMBER= ? AND ROOMRESERVATION.RESERVATIONID=?";
	public static final String DELETEFROMROOMRESERVEFACULTY ="DELETE FROM ROOMRESERVATION WHERE ROOMRESERVATION.FACULTYNUMBER= ? AND ROOMRESERVATION.RESERVATIONID=?";
	public static final String DELETEFROMROOMCHECKOUT ="DELETE FROM CHECKOUTROOM WHERE CHECKOUTROOM.RESERVATIONID=? AND CHECKINTIME IS NOT null";
	public static final String DELETEFROMCAMERARESERVESTUDENT ="DELETE FROM CAMERARESERVATION WHERE CAMERARESERVATION.STUDENTNUMBER= ? AND CAMERARESERVATION.RESERVATIONID=?";
	public static final String DELETEFROMCAMERARESERVEFACULTY ="DELETE FROM CAMERARESERVATION WHERE CAMERARESERVATION.FACULTYNUMBER= ? AND CAMERARESERVATION.RESERVATIONID=?";
	public static final String DELETEFROMCAMERACHECKOUT ="DELETE FROM CAMERACHECKOUT WHERE CAMERACHECKOUT.RESERVATIONID=? AND CHECKINTIME IS NOT null";
	public static final String GETALLBOOKFINEFROMCHECKOUTLOGSTUDENT ="select * from CHECKOUTLOG WHERE STUDENTNUMBER = ? AND FINE > 0";
	public static final String GETALLBOOKFINEFROMCHECKOUTLOGSFACULTY ="select * from CHECKOUTLOG WHERE FACULTYNUMBER = ? AND FINE > 0";
	public static final String GETALLCAMERAFINEFROMCHECKOUTLOGSTUDENT ="select * from CAMERACHECKOUTLOG,CAMERA WHERE CAMERACHECKOUTLOG.CAMERAID = CAMERA.CAMERAID AND STUDENTNUMBER = ? AND FINE > 0";
	public static final String GETALLCAMERAFINEFROMCHECKOUTLOGFACULTY ="select * from CAMERACHECKOUTLOG,CAMERA WHERE CAMERACHECKOUTLOG.CAMERAID = CAMERA.CAMERAID AND FACULTYNUMBER = ? AND FINE > 0";
	public static final String UPDATEALLBOOKFINEFROMCHECKOUTLOGSTUDENT ="UPDATE CHECKOUTLOG SET FINE = 0 WHERE STUDENTNUMBER = ?";
	public static final String UPDATEALLBOOKFINEFROMCHECKOUTLOGFACUTY ="UPDATE CHECKOUTLOG SET FINE = 0 WHERE FACULTYNUMBER = ?";
	public static final String UPDATEALLCAMERAFINEFROMCHECKOUTLOGSTUDENT ="UPDATE CAMERACHECKOUTLOG SET FINE = 0 WHERE STUDENTNUMBER = ?";
	public static final String UPDATEALLCAMERAFINEFROMCHECKOUTLOGFACULTY ="UPDATE CAMERACHECKOUTLOG SET FINE = 0 WHERE FACULTYNUMBER = ?";
	public static final String GETTILEANDTYPEFROMBOOKBYRESOURCEID = "SELECT * FROM BOOK WHERE RESOURCEID = ?";
	public static final String GETTILEANDTYPEFROMCONFERENCEBYRESOURCEID = "SELECT * FROM CONFERENCE WHERE RESOURCEID = ?";
	public static final String GETTILEANDTYPEFROMJOURNALBYRESOURCEID = "SELECT * FROM JOURNAL WHERE RESOURCEID = ?";
	public static final String GETALLJOURNALSCHECKEDOUTFORSTUDENT = "SELECT JOURNAL.*,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY, JOURNAL.RESOURCEID FROM JOURNAL,LIBRARY,LIBRARYCATEGORY,CHECKOUT WHERE JOURNAL.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND JOURNAL.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CHECKOUT.RESOURCEID=JOURNAL.RESOURCEID AND CHECKOUT.STUDENTNUMBER= ? AND CHECKINTIME is null";
	public static final String GETALLJOURNALSCHECKEDOUTFORFACULTY = "SELECT JOURNAL.*,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY, JOURNAL.RESOURCEID FROM JOURNAL,LIBRARY,LIBRARYCATEGORY,CHECKOUT WHERE JOURNAL.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND JOURNAL.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CHECKOUT.RESOURCEID=JOURNAL.RESOURCEID AND CHECKOUT.FACULTYNUMBER= ? AND CHECKINTIME is null";
	public static final String GETALLCONFERENCESCHECKEDOUTFORSTUDENT = "SELECT CONFERENCE.*,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY, CONFERENCE.RESOURCEID FROM CONFERENCE,LIBRARY,LIBRARYCATEGORY,CHECKOUT WHERE CONFERENCE.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CONFERENCE.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CHECKOUT.RESOURCEID=CONFERENCE.RESOURCEID AND CHECKOUT.STUDENTNUMBER= ? AND CHECKINTIME is null";
	public static final String GETALLCONFERENCESCHECKEDOUTFORFACULTY = "SELECT CONFERENCE.*,LIBRARY.LIBRARYID AS LIBRARYID,LIBRARYCATEGORY.CATEGORYID AS CATEGORYID,LIBRARY.LIBRARYNAME,LIBRARYCATEGORY.CATEGORY, CONFERENCE.RESOURCEID FROM CONFERENCE,LIBRARY,LIBRARYCATEGORY,CHECKOUT WHERE CONFERENCE.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CONFERENCE.CATEGORYID = LIBRARYCATEGORY.CATEGORYID AND LIBRARY.LIBRARYID = LIBRARYCATEGORY.LIBRARYID AND CHECKOUT.RESOURCEID=CONFERENCE.RESOURCEID AND CHECKOUT.FACULTYNUMBER= ? AND CHECKINTIME is null";
	public static final String GETFACULTYINFO = "SELECT * FROM FACULTY,FACULTYCATEGORY where FACULTY.CATEGORYID = FACULTYCATEGORY.CATEGORYID AND FACULTY.FACULTYNUMBER = ?";
	public static final String UPDATEFACULTY = "UPDATE FACULTY SET FNAME=?,LNAME=?,NATIONALITY=?,DEPARTMENT=?,CATEGORYID=? WHERE FACULTYNUMBER = ?";
	public static final String GETCATEGORYMAP = "SELECT * FROM FACULTYCATEGORY";
	
	public static final String EXECUTEUPDATEREMINDERPUBLICATIONS="execute  UpdateReminderPublications (?,?)";
	public static final String EXECUTEUPDATEROOMRESERVATION="execute  UpdateRoomReservations";
	public static final String EXECUTEUPDATEROOMRESERVATION1="execute  UpdateRoomReservations1";
	public static final String EXECUTEPUBLICATIONPRIORITY="execute  PublicationPriority";
	public static final String EXECUTECAMERACANCELLATION="execute  CameraCancellation";
	public static final String EXECUTECAMERACONFIRMATION="execute  CameraConfirmation";
	public static final String CHECKOUT_E_PUBLICATION="INSERT INTO epublicationcheckout VALUES(?,?,?,?)";
	
	public static final String UPDATESTUDENTHOLD="DELETE from studenthold where studentnumber=?";
	public static final String CHECKSTUDENTHOLD="select count(*) from studenthold where studentnumber=?";
}

