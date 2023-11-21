#include "Lcd.h"
#include "Arduino.h"

#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd = LiquidCrystal_I2C(0x27,20,4); 


Lcd::Lcd(int sda, int scl) {
    this->sda = sda;
    this->scl = scl;
    lcd.begin(20,4);
    lcd.backlight();
    pinMode(9,OUTPUT);
    digitalWrite(9,50);
    
}

void Lcd::display(char * msg) {
    lcd.print(msg);
}