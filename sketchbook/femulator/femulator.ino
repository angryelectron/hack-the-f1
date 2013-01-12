/*
 * Basic sketch for testing.
 */

#define PIN 13

void setup() {
  Serial.begin(9600);
  pinMode(PIN, INPUT);
}

void loop() {
  Serial.print(digitalRead(PIN));
  delay(1);
}
