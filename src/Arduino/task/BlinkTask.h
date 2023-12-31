#ifndef __BLINK_TASK__
#define __BLINK_TASK__

#include "Arduino/kernel/Task.h"
#include "Arduino/components/Led.h"

class BlinkTask: public Task {

  int pin;
  Light* led;
  enum { ON, OFF} state;

public:

  BlinkTask(int pin);  
  void init(int period);  
  void tick();
};

#endif

