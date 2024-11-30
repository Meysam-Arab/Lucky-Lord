<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/21/2017
 * Time: 3:42 PM
 */

namespace App\Providers;

use App\User;
use Carbon\Carbon;
use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Contracts\Auth\UserProvider;
use Log;
use Hash;

class CustomUserProvider implements UserProvider {

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param  mixed $identifier
     * @return \Illuminate\Contracts\Auth\Authenticatable|null
     */
    public function retrieveById($identifier)
    {
        $qry = User::where('user_id','=',$identifier);

        if($qry->count() >0)
        {
            $user = $qry->select('user_id', 'user_guid', 'user_name','luck','hazel', 'password')->first();

            $attributes = array(
                'user_id' => $user->user_id,
                'user_name' => $user->user_name,
                'password' => $user->password,
            );

            return $user;
        }
        return null;
    }

    /**
     * Retrieve a user by by their unique identifier and "remember me" token.
     *
     * @param  mixed $identifier
     * @param  string $token
     * @return \Illuminate\Contracts\Auth\Authenticatable|null
     */
    public function retrieveByToken($identifier, $token)
    {
        $qry = UserPoa::where('user_id','=',$identifier)->where('remember_token','=',$token);

        if($qry->count() >0)
        {
            $user = $qry->select('user_id','user_guid', 'user_name','luck','hazel', 'password')->first();

            $attributes = array(
                'user_id' => $user->user_id,
                'user_name' => $user->user_name,
                'password' => $user->password,
            );

            return $user;
        }
        return null;
    }

    /**
     * Update the "remember me" token for the given user in storage.
     *
     * @param  \Illuminate\Contracts\Auth\Authenticatable $user
     * @param  string $token
     * @return void
     */
    public function updateRememberToken(Authenticatable $user, $token)
    {
        $user->setRememberToken($token);
        $user->save();

    }

    /**
     * Retrieve a user by the given credentials.
     *
     * @param  array $credentials
     * @return \Illuminate\Contracts\Auth\Authenticatable|null
     */
    public function retrieveByCredentials(array $credentials)
    {
        $qry = User::where('user_name','=',$credentials['user_name']);
        if($qry->count() >0)
        {
            $user = $qry->select('user_id','user_guid','user_name','luck','hazel','password')->first();
            return $user;
        }
        return null;


    }

    /**
     * Validate a user against the given credentials.
     *
     * @param  \Illuminate\Contracts\Auth\Authenticatable $user
     * @param  array $credentials
     * @return bool
     */
    public function validateCredentials(Authenticatable $user, array $credentials)
    {
        // we'll assume if a user was retrieved, it's good

        if(Hash::check($credentials['password'], $user->getAuthPassword() )  && $user->user_name == $credentials['user_name'] )
        {

//            $user->last_login_time = Carbon::now();
            $user->save();

            return true;
        }
        return false;


    }

}