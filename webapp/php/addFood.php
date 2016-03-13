<?php

    $config = require "../../../config_foodapp.php";

    $servername = $config["name"];
    $serverusername = $config["username"];
    $serverpassword = $config["password"];
    $database = $config["database"];

    if(isset($_POST["ImageName"])) //mobile upload
    {
    	$imgname = $_POST["ImageName"];
    	$imgsrc = base64_decode($_POST["base64"]);
    	$fp = fopen(__DIR__."/../userImg/".$imgname, 'w');
    	$bytes = fwrite($fp, $imgsrc);
    	fclose($fp);
    }
    else if(isset($_FILES["file"])) //web upload
    {
		$uploaddir = __DIR__."/../userImg/";
        $uploadPartFile = strtolower(time() . "." . pathinfo($_FILES["file"]["name"], PATHINFO_EXTENSION));
		$uploadfile = $uploaddir . $uploadPartFile;
        move_uploaded_file($_FILES["file"]["tmp_name"], $uploadfile);
    }
    else
    {
        error_log("WTF JUST HAPPENED (THERE WAS NO PICTURE PASSED)");
        return;
    }

    $name = $_POST["name"];
    $username = "charliepdz"; //temporary <---------------------
    $description = $_POST["description"];
    $location = $_POST["location"];
    $price = $_POST["price"];
    $contact = $_POST["contact"];
    $tags = $_POST["tags"];
    $time = $_POST["timeCooked"];

    $conn = new mysqli($servername, $serverusername, $serverpassword, $database);

    $data = "INSERT INTO Food (name, description, location, price, time, image, username, tags, contactInfo)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    $stmt = $conn->prepare($data);
    $stmt->bind_param('sssdsssss', $name, $description, $location, $price, $time, $uploadPartFile, $username, $tags, $contact);
    $stmt->execute();

    echo $conn->insert_id;

    $stmt->close();

?>