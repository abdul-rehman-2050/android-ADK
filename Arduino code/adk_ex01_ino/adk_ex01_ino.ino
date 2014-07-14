#include <AndroidAccessory.h>

AndroidAccessory acc("Google",
		     "DemoKit",
		     "DemoKit Arduino Board",
		     "1.0",
		     "http://www.android.com",
		     "0000000012345678");
void setup();
void loop();

void setup()
{
	Serial.begin(115200);
        pinMode(13, OUTPUT); 
        pinMode(12, OUTPUT);
        pinMode(11, OUTPUT);
        pinMode(10, OUTPUT);
        pinMode(9, OUTPUT);
        pinMode(8, OUTPUT);
        pinMode(A0, INPUT);
        pinMode(A1, INPUT);
        pinMode(A2, INPUT);
        pinMode(A3, INPUT);
        pinMode(A4, INPUT);
        pinMode(A5, INPUT);
	Serial.print("\r\nStart");
	acc.begin();
}

void loop()
{
  byte msg[3];
  
	if (acc.isConnected()) {
                Serial.print("Accessory connected. ");
		int len = acc.read();
                if(len==255)
                 {
                   digitalWrite(13,HIGH);
                   acc.write('a');  //command type
                   //acc.write(0);  //low byte
                   acc.write(1);
                   acc.write(255);  //high byte
                   
                 }
                 else if(len==0)
                 {
                   
                   digitalWrite(13,LOW);
                   acc.write('b');  //command type
                   acc.write(2);    //low byte
                   acc.write(23);    //high byte
                   
                 }
                Serial.print("Message length: ");
                Serial.println(len, DEC);
        }

	delay(100);
}

