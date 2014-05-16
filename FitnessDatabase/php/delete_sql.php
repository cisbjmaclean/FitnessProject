<?php
/*
 * Coded By: Dmitri Tulonen 
 * Coded For: CIS_2250 Mobile Application Development
 * Date: 22/01/2014
 */

/*
 * Following code will delete a row from a table
 * 
 * USEAGE
 * http://localhost/delete_sql.php?sql=tableNames&primarykey=primaryKey&primaryinfo=PrimaryKeyInformation
 *
 * Not Delimited
 * SQL = Table Name
 * Primary Key = Column Name Of The Primary Key
 * Primary Info = Data Stored In The Primary Key Field You Wish To Modify
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_GET['sql']) && isset($_GET['primarykey']) && isset($_GET['primaryinfo'])) {
	$primarykey = $_GET['primarykey'];
    $primaryinfo = $_GET['primaryinfo'];
    $sql = $_GET['sql'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched primary key
    $result = mysql_query("DELETE FROM $sql WHERE $primarykey = $primaryinfo");
    
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // not found
        $response["success"] = 0;
        $response["message"] = "Not found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>