<?php
namespace App\Http\Controllers;
use App\Question;
use Carbon\Carbon;
use App\User;
use Illuminate\Http\Request;
use App\OperationMessage;
use League\Flysystem\Filesystem;
use Mockery\Tests\Chatroulette_ConnectionInterface;
use Monolog\Handler\SocketHandler;
use Validator;
use Redirect;
use Session;
use Auth;
use DB;
use File;
use Log;
use App\RequestResponseAPI;

use Exception;
use Route;
use App\LogEvent;
use Chumper\Zipper\Zipper;
use ZipArchive;
use URL;

use Illuminate\Support\Facades\Storage;

class testController extends Controller
{
        public function testZipFile(){
            SocketHandler::
            $zip = new ZipArchive;
            if ($zip->open('fonts/Yekan.zip') === TRUE) {

                $zip->extractTo('testing');
                $zip->close();
                echo 'ok';
            } else {
                echo 'failed';
            }
        }
}