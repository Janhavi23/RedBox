package com.example.red_box;


public class Email {
    String decrypted_Email ;

    public Email(){};

    public Email(String decrypted_Email) {
        this.decrypted_Email = decrypted_Email;
    }

    public String getDecrypted_Email() {
        return decrypted_Email;
    }

    public void setDecrypted_Email(String decrypted_Email) {
        this.decrypted_Email = decrypted_Email;
    }
}
