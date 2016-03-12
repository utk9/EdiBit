<?php

    $config = require "../../../config_foodapp.php";

    $servername = $config["name"];
    $serverusername = $config["username"];
    $serverpassword = $config["password"];
    $database = $config["database"];

    if(isset($_POST["ImageName"]))
    {
    	$imgname = $_POST["ImageName"];
    	$imgsrc = base64_decode($_POST["base64"]);
    	$fp = fopen(__DIR__."/../userImg/".$imgname, 'w');
    	$bytes = fwrite($fp, $imgsrc);
    	fclose($fp);
    }
    else if(isset($_FILES["file"]))
    {
		$uploaddir = __DIR__."/../userImg/";
        $uploadPartFile = time() . "." . pathinfo($_FILES["file"]["name"], PATHINFO_EXTENSION);
		$uploadfile = strtolower($uploaddir . $uploadPartFile);
        move_uploaded_file($_FILES["file"]["tmp_name"], $uploadfile);
    }
    else
    {
        error_log("WTF JUST HAPPENED");
        return;
    }

    $name = $_POST["name"];
    $description = $_POST["description"];
    $location = $_POST["location"];
    $price = $_POST["price"];
    $contact = $_POST["contact"];
    $time = time();

    $conn = new mysqli($servername, $serverusername, $serverpassword, $database);

    $data = "INSERT INTO Food (name, description, location, price, time, image, username, tags, contactInfo)
    VALUES ('$name', '$description', '$location', '$price', '$time', '$uploadPartFile', 'charliepdz', 'blah, blah', '$contact')";

    if ($conn->query($data) === TRUE) {
        echo $conn->insert_id;
    }
    else
    {
        error_log("Well shit");
    }

?>