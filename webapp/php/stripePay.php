<?php 

require_once('stripe/init.php');

\Stripe\Stripe::setApiKey("sk_test_03KMl1QqgdEMgD5zRT1Fxdw6");

// Get the credit card details submitted by the form
$token = $_POST['stripeToken'];
$paymentAmount = $_POST['paymentAmount'];

error_log($token);
error_log($paymentAmount);

// Create the charge on Stripe's servers - this will charge the user's card
try {
  $charge = \Stripe\Charge::create(array(
    "amount" => $paymentAmount*100, // amount in cents, again
    "currency" => "cad",
    "source" => $token,
    "description" => "Edibit charge"
    ));
  	echo 1;
} catch(\Stripe\Error\Card $e) {	
	echo 0;
  // The card has been declined
}

?>