#ifndef __TESTSONAR__
#define __TESTSONAR__

#include "components/Sonar.h"

class TestSonar {
    public:
        TestSonar();
        void testDistance();

    private:    
        Sonar* sonar;
};

#endif