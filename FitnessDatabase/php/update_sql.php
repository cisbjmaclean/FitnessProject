<?php
/*
 * Coded By: Dmitri Tulonen 
 * Coded For: CIS_2250 Mobile Application Development
 * Date: 22/01/2014
 */
 
/*
 * Following code will update any information
 *
 * USEAGE
 * http://localhost/update_sql.php?sql=tableName&fields=field1~field2parameters=param1~param2&primarykey=PrimaryKeyCollumName&primaryinfo=ValueOfThePrimaryKey
 *
 * Delimited By Tilde "~"
 * Fields = Column Names You Would Like To Modify
 * Parameters = Information You Would Like To Put In The Columns
 * 
 * Not Delimited
 * SQL = Table Name
 * Primary Key = Column Name Of The Primary Key
 * Primary Info = Data Stored In The Primary Key Field You Wish To Modify
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_GET['sql']) && isset($_GET['primarykey']) && isset($_GET['primaryinfo']) && isset($_GET['fields']) && isset($_GET['parameters']) ) {
    
    $sql = $_GET['sql'];
    $primarykey = $_GET['primarykey'];
    $primaryinfo = $_GET['primaryinfo'];
    $fields = null;
	$parameters = null;
	
	// make a list of the fields/parameters the person wishes to set tilde "~" as a deliminator
	if (isset($_GET['fields']) && isset($_GET['parameters'])){
		$fields = explode("~", $_GET['fields']);
		$parameters = explode("~", $_GET['parameters']);
		//print_r($fields);
	}
	$fieldParamString = "";
	// loop through and create a string of the fields to update matched with their parameters
	for($i = 0;$i < count($fields);$i++) {
		if($i < (count($fields) - 1)){
			$fieldParamString = $fieldParamString . $fields[$i] . " = " . "'" . $parameters[$i] . "'" . ", ";
		} else {
			$fieldParamString = $fieldParamString . $fields[$i] . " = " . "'" . $parameters[$i] . "'";
		}	
	}
		
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE $sql SET $fieldParamString WHERE $primarykey = $primaryinfo");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
