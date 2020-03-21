package com.example.hans.agrigo.MenuRegister;

public interface IAuthRegis {

    void InitView();

    void Signup();

    void onSignupSucces();

    void onSignupFailed();

    void onNetworkFailed(
            String cause
    );
}
