using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WeatherWCFService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WeatherService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WeatherService.svc or WeatherService.svc.cs at the Solution Explorer and start debugging.
    public class WeatherService : IWeatherService
    {

        public string MyFunction()
        {
            return "Hello World!";
        }
    }
}
