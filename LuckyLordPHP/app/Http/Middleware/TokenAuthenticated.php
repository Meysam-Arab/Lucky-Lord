<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 2/25/2017
 * Time: 3:35 PM
 */

namespace App\Http\Middleware;

use App\RequestResponseAPI;
use App\User;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Exceptions\TokenBlacklistedException;
use Tymon\JWTAuth\Exceptions\TokenExpiredException;
use \Tymon\JWTAuth\Middleware\BaseMiddleware;
use Log;
use JWTAuth;

class TokenAuthenticated extends BaseMiddleware
{

    public function handle($request, \Closure $next)
    {
        $user = null;
        $oldToken = null;
        if (! $token = $this->auth->setRequest($request)->getToken()) {
//            return json_encode(['error' => RequestResponseAPI::ERROR_TOKEN_INVALID, 'tag' => $request->input('tag')]);
            return $this->respond('tymon.jwt.absent', 'token_not_provided', 400);
//            return $this->respond('tymon.jwt.absent', RequestResponseAPI::ERROR_TOKEN_INVALID, 400);

        }
        try {
            ////meysam - added in 13970118
            $oldToken = $token;
            $user = $this->auth->authenticate($token);
        } catch (TokenExpiredException $e) {
            try
            {
//                //meysam - added in 13970118
                $token=JWTAuth::refresh($token);
                session(['tokenRefreshed' => true]);
                session(['token' => $token]);
                JWTAuth::setToken($token);
                $user = $this->auth->authenticate($token);
                if($user != null)
                    User::updateToken($token,$user->user_id,$user->user_guid);
            }
            catch (TokenBlacklistedException $tbex)
            {
//                //meysam - added in 13970118
                //this must not happen !!!!!!! we disabled it....
                if($user != null)
                {
                    $payloadable = [
                        'user_id' => $user->user_id,
                        'user_name' => $user->user_name
                    ];

                    $token = JWTAuth::fromUser($user,$payloadable);
                }
                else
                {
//
                    $user = User::findByToken($oldToken);
                    if($user != null) {
                        $payloadable = [
                            'user_id' => $user->user_id,
                            'user_name' => $user->user_name
                        ];

                        $token = JWTAuth::fromUser($user, $payloadable);
                        $user = $this->auth->authenticate($token);
//                    return $this->respond('tymon.jwt.blacklisted', RequestResponseAPI::ERROR_TOKEN_BLACKLISTED_CODE, $e->getStatusCode(), [$e]);
                        $this->events->fire('tymon.jwt.valid', $user);
                        return $next($request);
                    }
                }

            }
            $user = $this->auth->authenticate($token);
        } catch (JWTException $e) {
//            //meysam - added in 13970118
//            return json_encode(['error' => RequestResponseAPI::ERROR_TOKEN_INVALID, 'tag' => $request->input('tag')]);

            return $this->respond('tymon.jwt.invalid', 'token_invalid', $e->getStatusCode(), [$e]);
//              return $this->respond('tymon.jwt.invalid', RequestResponseAPI::ERROR_TOKEN_INVALID, $e->getStatusCode(), [$e]);

        }

        if (! $user) {
            return $this->respond('tymon.jwt.user_not_found', 'user_not_found', 404);
        }
        $this->events->fire('tymon.jwt.valid', $user);
        return $next($request);
    }
}
