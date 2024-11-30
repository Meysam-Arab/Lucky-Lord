
<?php
//header("content-type: text/html; charset=utf-8");
/**
 *  P H P S O C K E T . I O 
 * 
 *  a PHP 5 Socket API
 * 
 *  For more informations: {@link https://github.com/dhtml/phpsocket.io}
 *  
 *  @author Anthony Ogundipe
 *  @e-mail: diltony@yahoo.com
 *  @copyright Copyright (c) 2014 Anthony Ogundipe
 *  @license http://opensource.org/licenses/mit-license.php The MIT License
 *  @package phpsocket.io
 */
 
//include websockets dependencies
include "users.php";
include "websockets.php";
 
 
class PHPWebSockets  extends WebSocketServer{

    /**
     *  commands
     *  
     *  An object holding all available commands
     *
     *  @access public
     *
     *  @var  object
     *
     */
	public $commands;


    /**
     *  numUsers
     *  
     *  An integer holding number of users
     *
     *  @access public
     *
     *  @var  int
     *
     */
	public $numUsers=0;
	

    /**
     *  socketID
     *  
     *  An array holding all available socket IDs
     *
     *  @access public
     *
     *  @var  array
     *
     */
	public $socketID=Array();


    /**
     *  usernames
     *  
     *  An object holding all available users
     *
     *  @access public
     *
     *  @var  object
     *
     */
	public $usernames=Array();
	
	/**
    *  on
    *  
	*  This is used to package a request to the socket server
	*
    *  @param  string  $cmd     The command to be broadcasted e.g. chat
    *  @param  object  $cb      The function callback attached to the command
	*/
	public function on($cmd,$cb) {
		$this->commands["$cmd"]=$cb;
	}
	
	
	/**
    *  process
    *  
	*  This is called immediately when the data is recieved. and is used internally to send messages to clients. 
	*
    *  @param  object  $user     The user object of the client sending the message
    *  @param  object  $message  The message object to be sent
	*
	*  @access protected
	*/
	protected function process ($user, $message) 
    {
		//اولین تابعی که صدا زده می شود
		//file_put_contents("meysam.txt", "in phpsocket - in process - user: ".json_encode($user)." message: ".json_encode($message)." \n", FILE_APPEND);

		
		$this->user=$user;
		
		$message=json_decode($message);
		
		$message->data= isset($message->data) ? $message->data : '';
		$message->sender= isset($message->sender) ? $message->sender : 0;
		
		if($message->broadcast) {
		// داده قرار است به تمامی کاربران ارسال شود
		//broadcast message
		$message->broadcast=false;
		$data=json_encode($message);

		foreach($this->users as $user) {
			$this->send($user, $data);
		}
		
		} else {
			// دستور مربوطه را اجرا می کنیم
		//non-broadcast message

		$this->trigger($message->cmd,$message->data,$message->sender);
		}
	}
	
  /**
  * getUserById
  * 
  * Fetches a user object via its id
  * 
  */
  public function getUserById($userid) {
    foreach ($this->users as $user) {
      if ($user->id == $userid) {
        return $user;
      }
    }
    return null;
  }

  /**
  * getUserByName
  * 
  * Fetches a user object by the Name
  * 
  */
  public function getUserByName($username) {
	  		//file_put_contents("meysam.txt", "meysam sio 4 - in getUserByName - username: ".json_encode($username)." \n", FILE_APPEND);

			//meysam added
			$meysamLastUserId = null;
			//$keyArrayToRemove = array();
	  
    foreach ($this->socketID as $uid=>$uname) {
			  		//file_put_contents("meysam.txt", "meysam sio 5 - in getUserByName - loop username: ".json_encode($uname)." \n", FILE_APPEND);
			  		//file_put_contents("meysam.txt", "meysam sio 5 - in getUserByName - loop uid: ".json_encode($uid)." \n", FILE_APPEND);

      if ($uname == $username) {
		  $meysamLastUserId = $uid;
		  //array_push($keyArrayToRemove,$uid);
		  
		  //$user=$this->getUserById($uid);
        //return $user;
      }
    }
	
	//meysam added
	if($meysamLastUserId != null)
	{
		//foreach ($keyArrayToRemove as $value) {
    
			  //if ($value != $uid) {
				//unset($this->sockets[$value]);
				//unset($this->users[$value]);
			  //}
			//}
		 $user=$this->getUserById($meysamLastUserId);
        return $user;
	}
	
    return null;
  }

	
	/**
    *  trigger
    *  
	* This will trigger a command that has already been using using the on function
	*
    *  @param  string  $cmd     The command to be broadcasted e.g. chat
    *  @param  string  $data    (Optional) The data to be broadcasted along with the command e.g. hello world
    *  @param  string  $sender   (Optional) the id of the user that is sending the message
	*/
	public function trigger($cmd,$params='',$sender=null) {
		if(!isset($this->commands["$cmd"])) {return;}
		$this->commands["$cmd"]($this,$params,$sender);
	}
	
	/**
    *  connected
    *  
    *  This is executed when socket connection is established for a particular user
	*  A welcome message is also send back to the client
	*
    *  @param  object  $user     The user object of the client sending the message
	*
	*  @access protected
	*
    */
    protected function connected ($user) 
    {
		//وقتی یک کاربر خاص به پورت وصل می شود صدا زده می شود
		
		$this->user=$user;
		$this->trigger("connect",$user->id);
    }
	
    /**
    *  disconnect
    *  
    *   This is executed when a client is disconnected. It is a cleanup function.
	*
    *   @param  object   $socket    			The socket object of the connected client
    *   @param  boolean  $triggerClosed   		Flag to determine if close was triggered by client
    *   @param  boolean  $sockErrNo   			(optional) Socket error number
	*
	*   @access protected
    */
	 protected function disconnect($socket, $triggerClosed = true, $sockErrNo = null) 
	 {
		//////////////////////////meysam - added - start ///////////////////////////////
		
	
		
		//file_put_contents("meysam.txt", "in phpsocket - in disconnect - numUsers value: ".json_encode($this->numUsers)." \n", FILE_APPEND);

		$disconnectedUser = $this->getUserBySocket($socket);
		//file_put_contents("meysam.txt", "in phpsocket - in disconnect - desconnected user id:".json_encode($disconnectedUser->id)." \n", FILE_APPEND);
		
		//file_put_contents("meysam.txt", "in phpsocket - in disconnect - keys in socketID array: ".json_encode(array_keys($this->socketID))." \n", FILE_APPEND);
		
		
		//file_put_contents("meysam.txt", "in phpsocket - in disconnect - keys in users array: ".json_encode(array_keys($this->users))." \n", FILE_APPEND);

		//file_put_contents("meysam.txt", "in phpsocket - in disconnect - keys in userNames array: ".json_encode(array_keys($this->usernames))." \n", FILE_APPEND);

		$this->trigger("disconnect",$disconnectedUser->id);
	
		////////////////// meysam - added - end ///////////////////////////////////////
		
		//file_put_contents("meysam.txt", "in phpsocket - in disconnect - after trigger \n", FILE_APPEND);


		//meysam commented
	  //$this->trigger("disconnect",$this->user->id);
	  parent::disconnect($socket, $triggerClosed, $sockErrNo);
	  
	  //file_put_contents("meysam.txt", "in phpsocket - in disconnect - after parent_disconnect \n", FILE_APPEND);
	  //file_put_contents("meysam.txt", "in phpsocket - in disconnect - keys after in users array: ".json_encode(array_keys($this->users))." \n", FILE_APPEND);

	  
	 }
     
    /**
    *  closed
    *  
    *   This is where cleanup would go, in case the user had any sort of
    *   open files or other objects associated with them.  This runs after the socket 
    *   has been closed, so there is no need to clean up the socket itself here.
	*
    *   @param  object  $user    The user object of the connected client
	*
	*   @access protected
    */
    protected function closed ($user) 
    {
    }
	
	/**
    *  emit
    *  
	* send message to current user only
	*
    *  @param  string  $cmd     The command to be broadcasted e.g. chat
    *  @param  string  $data    (Optional) The data to be broadcasted along with the command e.g. hello world
	*/
	public function emit($cmd,$data) {
		//file_put_contents("meysam.txt", "in phpsocket - in emit - data: ".json_encode($data)." cmd: ".json_encode($cmd)."   \n", FILE_APPEND);

		$this->send($this->user, $this->cmdwrap($cmd,$data));
	}
	
	/**
    *   push
    *  
	*   send message to specified user only
	*
    *   @param  object  $user    The user object of the recipient (or the user id)
    *   @param  string  $cmd     The command to be broadcasted e.g. chat
    *   @param  string  $data    (Optional) The data to be broadcasted along with the command e.g. hello world
	*/
	public function push($user,$cmd,$data) {
		//file_put_contents("meysam.txt", "in phpsocket - in push - data: ".json_encode($data)." cmd: ".json_encode($cmd)." user: ".json_encode($user)."   \n", FILE_APPEND);

		$this->send($user, $this->cmdwrap($cmd,$data));
	}
	
	/**
    *  cmdwrap
    *  
	* This is used internally to package the entire information of command, data and sender into a json object
	*
    *  @param  string  $cmd     The command to be broadcasted e.g. chat
    *  @param  string  $data    (Optional) The data to be broadcasted along with the command e.g. hello world
    *  @param  string  $sender   (Optional) the id of the user that is sending the message
	*/
	private function cmdwrap($cmd,$data,$sender=null) {
		$response=array('cmd'=>$cmd,'data'=>$data,'sender'=>$this->user->id);
		return json_encode($response);
	}


	/**
    *  broadcast
	*
	* This is used to send a message to all connected users
	*
    *  @param  string  $cmd     The command to be broadcasted e.g. chat
    *  @param  string  $data    (Optional) The data to be broadcasted along with the command e.g. hello world
    *  @param  boolean  $self   (Optional) true means the message should also be broadcasted to the sender
	*/
	public function broadcast($cmd,$data='',$self=false) {
		$data=$this->cmdwrap($cmd,$data);

		foreach($this->users as $user) {
			if(!$self && $user==$this->user) {continue;}
			$this->send($user, $data);
		}
	}
	
	/**
	* get_all_users
	*
	* Returns an array of all available user IDs
	*/
	public function get_all_users() {
		$users=Array();
		foreach($this->users as $user) {
			$users[]=$user->id;
		}
		return $users;
	}
	
	/**
	* listen
	*
	* This will initiate the websocket server and start waiting for client connections
	*/
	public function listen() {
		try {
			$this->run();
		}
		catch (Exception $e) {
			$this->stdout($e->getMessage());
		}
	}
	
	//meysam added
	//public function unsetValue(array $array, $value, $strict = TRUE)
	//{
		//if(($key = array_search($value, $array, $strict)) !== FALSE) {
			//unset($array[$key]);
		//}
		//return $array;
	//}

}
