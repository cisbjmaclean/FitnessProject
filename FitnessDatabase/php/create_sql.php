<?php
/*
 * Coded By: Dmitri Tulonen 
 * Coded For: CIS_2250 Mobile Application Development
 * Date: 22/01/2014
 */
 
/*
 * Following code will create new objects in tables
 * All details are read from HTTP GET Request
 * USEAGE
 * http://localhost/create_sql.php?sql=tableName&fields=field1~field2&parameters=param1~param2
 *
 * TableName = table you want to add to
 * Bellow are delimited fields by the Tilde "~"
 * Field = is the fields needed to insert (exclude auto incrementing ones)
 * Parameters = the information you would like to insert (in the same order as you list the fields)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_GET['sql']) && isset($_GET['fields']) && isset($_GET['parameters'])) {
    
	$fields = null;
	$parameters = null;
	$query = $_GET['sql'];
	
	// make a list of the fields the person wishes to set tilde "~" as a deliminator
	if (isset($_GET['fields'])){
		$fields = explode("~", $_GET['fields']);
		//print_r($fields);
	}
	$fieldString = "";
	// loop through and create a string of the fields
	for($i = 0;$i < count($fields);$i++) {
		if($i < (count($fields) - 1)){
			$fieldString = $fieldString . $fields[$i] . ", ";
		} else {
			$fieldString = $fieldString . $fields[$i];
		}	
	}
	// make a list of the parameters the person wishes to set using tilde "~" as a deliminator
	if (isset($_GET['parameters'])){
		$parameters = explode("~", $_GET['parameters']);
		//print_r($parameters);
	}
	$parametersString = "";
	// loop through and create a string of the parameters
	for($i = 0;$i < count($parameters);$i++) {
		if($i < (count($parameters) - 1)){
			$parametersString = $parametersString . "'" .$parameters[$i] . "'" . ", ";
		} else {
			$parametersString = $parametersString . "'" .$parameters[$i] . "'";
		}
	}
	
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO $query($fieldString) VALUES($parametersString)");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Successfully Added.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required information is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>