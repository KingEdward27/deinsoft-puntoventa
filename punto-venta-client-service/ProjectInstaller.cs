using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration.Install;
using System.Linq;
using System.ServiceProcess;
using System.Diagnostics;

namespace punto_venta_client_service
{
    [RunInstaller(true)]
    public partial class ProjectInstaller : System.Configuration.Install.Installer
    {
        //private ServiceProcessInstaller processInstaller;
        //private ServiceInstaller serviceInstaller;


        public ProjectInstaller()
        {
            InitializeComponent();
            this.AfterInstall += serviceInstaller1_AfterInstall;
            //processInstaller = new ServiceProcessInstaller();
            //serviceInstaller = new ServiceInstaller();

            //// Configuración básica del instalador
            //processInstaller.Account = ServiceAccount.LocalSystem;
            //serviceInstaller.ServiceName = "Punto de Venta Service";
            //serviceInstaller.StartType = ServiceStartMode.Automatic;

            //Installers.Add(processInstaller);
            //Installers.Add(serviceInstaller);

            // Evento para iniciar el servicio después de instalar
            //this.Committed += new InstallEventHandler(ProjectInstaller_Committed);
        }
        private void serviceInstaller1_AfterInstall(object sender, InstallEventArgs e)
        {
            using (ServiceController sc = new ServiceController(serviceInstaller1.ServiceName))
            {
                sc.Start();
            }
        }

        //private void ProjectInstaller_Committed(object sender, InstallEventArgs e)
        //{
        //    try
        //    {
        //        using (ServiceController sc = new ServiceController(serviceInstaller.ServiceName))
        //        {
        //            if (sc.Status != ServiceControllerStatus.Running)
        //            {
        //                sc.Start();
        //            }
        //        }
        //    }
        //    catch (Exception ex)
        //    {
        //        // Si querés registrar el error en el visor de eventos:
        //        EventLog.WriteEntry("Punto de Venta Service", "Error al iniciar el servicio automáticamente: " + ex.Message, EventLogEntryType.Error);
        //    }
        //}
    }

    
}
