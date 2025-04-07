using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;

namespace punto_venta_client_service
{
    static class Program
    {
        /// <summary>
        /// Punto de entrada principal para la aplicación.
        /// </summary>
        static void Main()
        {
            //var program = new Service1();
            //if (Environment.UserInteractive)
            //{
            //    program.Start();
            //}
            //else
            //{
            //    //ServiceBase.Run(new ServiceBase[] { program });
            //    ServiceBase[] ServicesToRun;
            //    ServicesToRun = new ServiceBase[]
            //    {
            //        new Service1()
            //    };
            //    ServiceBase.Run(ServicesToRun);
            //}

            ServiceBase[] ServicesToRun;
            ServicesToRun = new ServiceBase[]
            {
            new Service1()
            };
            ServiceBase.Run(ServicesToRun);
        }
    }
}
