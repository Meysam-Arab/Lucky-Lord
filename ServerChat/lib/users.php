
<?php
//header("content-type: text/html; charset=utf-8");
/*
* Websockets User Object
* From https://github.com/ghedipunk/PHP-Websockets
*/
class WebSocketUser {
  public $socket;
  public $id;
  public $name;
  public $headers = array();
  public $handshake = false;

  public $handlingPartialPacket = false;
  public $partialBuffer = "";

  public $sendingContinuous = false;
  public $partialMessage = "";
  
  public $hasSentClose = false;

  function __construct($id, $socket) {
    $this->id = $id;
    $this->socket = $socket;
  }
}