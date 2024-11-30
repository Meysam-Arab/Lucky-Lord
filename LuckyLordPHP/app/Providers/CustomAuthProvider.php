<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/21/2017
 * Time: 3:41 PM
 */
namespace App\Providers;

use Auth;
use Illuminate\Support\ServiceProvider;

class CustomAuthProvider extends ServiceProvider {

    /**
     * Bootstrap the application services.
     *
     * @return void
     */

    public function boot()
    {
        Auth::provider('custom', function($app, array $config) {
            return new CustomUserProvider();
        });
    }

    /**
     * Register the application services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

}