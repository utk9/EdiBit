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
    	if(fclose($fp))
    	{
    		echo 1;
    	}
    	else
    	{
    		echo 0;
    	}
    }

?>