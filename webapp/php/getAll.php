<?php
    $config = require "../../../config_foodapp.php";

    $servername = $config["name"];
    $serverusername = $config["username"];
    $serverpassword = $config["password"];
    $database = $config["database"];

    $json = array();

    // Create connection 
    $conn = new mysqli($servername, $serverusername, $serverpassword, $database);
    $data = "SELECT id, name, username, description, location, price, time, image, tags, contactInfo FROM Food";
    $result = $conn->query($data);
    if ($result->num_rows > 0) 
    { 
    	while($row = $result->fetch_assoc())
    	{
	        $newEntry = array(
                "id" => $row["id"],
	        	"name" => $row["name"],
	        	"username" => $row["username"],
	        	"description" => $row["description"],
	        	"location" => $row["location"],
	        	"price" => $row["price"],
	        	"time" => $row["time"],
	        	"image" => $row["image"],
                "tags" => explode(",", $row["tags"]),
                "contactInfo" => $row["contactInfo"]
	        );
	        $json[] = $newEntry;
    	}
    } 
    else 
    {
        echo "";
    }
    echo json_encode($json);

?>