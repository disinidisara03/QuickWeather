using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ServiceModel;

namespace WeatherHostApplication
{
    internal class Program
    {
        static void Main()
        {
            using (ServiceHost host = new ServiceHost(typeof(WeatherWCFService.WeatherService)))
            {
                host.Open();
                Console.WriteLine("Weather server Started");

                Console.ReadLine();
            }
        }
    }
}
