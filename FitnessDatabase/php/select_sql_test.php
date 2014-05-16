<?php
/*
 * Coded By: Dmitri Tulonen 
 * Coded For: CIS_2250 Mobile Application Development
 * Date: 22/01/2014
 */
 
/*
 * Following code will select everything from a table in the database and return it with JSON
 * Using The Code Requires
 * http://localhost/select_sql.php?sql=tableName&fields=field1~field2~field3
 */

// array for JSON response
$response = array();
$fields = null;

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();
// grab all the data from the table provided in the url
if (isset($_GET['sql'])){
	$query = $_GET['sql'];
	$result = mysql_query("SELECT * FROM $query") or die(mysql_error());
}

// make a list of the fields the person wishes to retrive currently using tilde "~" as a deliminator
if (isset($_GET['fields'])){
	$fields = explode("~", $_GET['fields']);
	//print_r($fields);
}

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    
    $response["CisFitness"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $CisFitness = array();
		// loop to get all columns requested
		foreach ($fields as &$value) {
			$CisFitness["$value"] = $row["$value"];
		}
        // push single object into final response array
        array_push($response["CisFitness"], $CisFitness);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no results found
    $response["success"] = 0;
    $response["message"] = "No results found";

    // echo no users JSON
    echo json_encode($response);
}
?>
