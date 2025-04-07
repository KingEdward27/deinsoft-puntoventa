using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.IO;
namespace punto_venta_client_service
{
    public partial class Service1 : ServiceBase
    {
        private Process p;
        private Thread monitorThread;
        private bool running = false;
        private string logPath;
        private string jarPath;

        public Service1()
        {
            InitializeComponent();
            this.ServiceName = "Punto de Venta Service";
            this.CanStop = true;
            this.CanPauseAndContinue = false;
            this.AutoLog = true;
        }
        protected override void OnStart(string[] args)
        {
            try
            {
                string path = Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location);
                Directory.SetCurrentDirectory(path);
                jarPath = Path.Combine(path, "punto-venta-client.jar");

                string fechaActual_YYYYMMDD = DateTime.Now.Year.ToString() + DateTime.Now.Month.ToString().PadLeft(2, '0') + DateTime.Now.Day.ToString().PadLeft(2, '0');

                //string directory = path + "/logs/";
                //if (!(Directory.Exists(directory)))
                //{
                //    Directory.CreateDirectory(directory);
                //}

                logPath = Path.Combine(path, "logs/punto-venta-client"+ fechaActual_YYYYMMDD + ".service.log");

                EventLog.WriteEntry("Iniciando servicio con jar: " + jarPath);

                StartJar();

                running = true;
                monitorThread = new Thread(() =>
                {
                    while (running)
                    {
                        if (p == null || p.HasExited)
                        {
                            File.AppendAllText(logPath, $"[{DateTime.Now}] El proceso del jar se ha detenido.\n");
                            running = false;
                        }
                        Thread.Sleep(1000);
                    }
                });

                monitorThread.IsBackground = true;
                monitorThread.Start();
            }
            catch (Exception ex)
            {
                EventLog.WriteEntry("Error en OnStart: " + ex.Message, EventLogEntryType.Error);
            }

        }

        //protected override void OnStop()
        //{
        //    StopJava();
        //    p.Close();

        //}
        //public void StopJava()
        //{
        //    p.Close();
        //    Process[] procs = Process.GetProcesses();
        //    if (procs.Length > 0)
        //    {
        //        foreach (Process proc in procs)
        //        {
        //            if (proc.ProcessName.ToLower().Contains("java")
        //                && proc.MainModule.FileName.Contains("Punto de Venta Service"))
        //            {
        //                proc.Kill();
        //            }

        //        }
        //    }
        //}
        //public void Start()
        //{
        //    String path = System.Reflection.Assembly.GetExecutingAssembly().Location;
        //    path = System.IO.Path.GetDirectoryName(path);
        //    Directory.SetCurrentDirectory(path);

        //    p = new Process();
        //    p.StartInfo.FileName = "cmd.exe";
        //    p.StartInfo.WindowStyle = ProcessWindowStyle.Normal;
        //    p.StartInfo.UseShellExecute = false;
        //    p.StartInfo.RedirectStandardOutput = true;
        //    p.StartInfo.RedirectStandardInput = true;
        //    p.StartInfo.CreateNoWindow = true;

        //    p.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;


        //    p.StartInfo.WorkingDirectory = path;
        //    EventLog.WriteEntry("path: " + path);
        //    p.Start();
        //    p.StandardInput.WriteLine(@"java -jar punto-venta-client.jar  >> punto-venta-client.log");

        //}
        public void StartJar()
        {
            string path = System.Reflection.Assembly.GetExecutingAssembly().Location;
            path = System.IO.Path.GetDirectoryName(path);
            Directory.SetCurrentDirectory(path);

            string jarPath = Path.Combine(path, "punto-venta-client.jar");
            string logPath = Path.Combine(path, "punto-venta-client.log");

            EventLog.WriteEntry("Intentando iniciar el jar en: " + jarPath);

            p = new Process();
            p.StartInfo.FileName = "java";
            p.StartInfo.Arguments = $"-jar \"{jarPath}\"";
            p.StartInfo.WorkingDirectory = path;

            p.StartInfo.UseShellExecute = false;
            p.StartInfo.RedirectStandardOutput = true;
            p.StartInfo.RedirectStandardError = true;
            p.StartInfo.CreateNoWindow = true;

            // Redirigir salida a archivo log
            p.OutputDataReceived += (sender, args) =>
            {
                if (args.Data != null)
                    File.AppendAllText(logPath, args.Data + Environment.NewLine);
            };

            p.ErrorDataReceived += (sender, args) =>
            {
                if (args.Data != null)
                    File.AppendAllText(logPath, "ERROR: " + args.Data + Environment.NewLine);
            };

            try
            {
                p.Start();
                p.BeginOutputReadLine();
                p.BeginErrorReadLine();

                EventLog.WriteEntry("El jar fue iniciado correctamente.");
            }
            catch (Exception ex)
            {
                EventLog.WriteEntry("Error al iniciar el jar: " + ex.Message);
            }
        }
        //private void StartJar()
        //{
        //    p = new Process();
        //    p.StartInfo.FileName = "java";
        //    p.StartInfo.Arguments = $"-jar \"{jarPath}\"";
        //    p.StartInfo.WorkingDirectory = Path.GetDirectoryName(jarPath);
        //    p.StartInfo.UseShellExecute = false;
        //    p.StartInfo.RedirectStandardOutput = true;
        //    p.StartInfo.RedirectStandardError = true;
        //    p.StartInfo.CreateNoWindow = true;

        //    p.OutputDataReceived += (sender, args) =>
        //    {
        //        if (args.Data != null)
        //            File.AppendAllText(logPath, args.Data + Environment.NewLine);
        //    };

        //    p.ErrorDataReceived += (sender, args) =>
        //    {
        //        if (args.Data != null)
        //            File.AppendAllText(logPath, "ERROR: " + args.Data + Environment.NewLine);
        //    };

        //    p.Start();
        //    p.BeginOutputReadLine();
        //    p.BeginErrorReadLine();
        //}
        protected override void OnStop()
        {
            running = false;

            try
            {
                if (p != null && !p.HasExited)
                {
                    p.Kill(); // Puedes implementar una forma más limpia si tu JAR la soporta
                    p.WaitForExit(5000);
                    p.Dispose();
                }

                if (monitorThread != null && monitorThread.IsAlive)
                    monitorThread.Join();

                File.AppendAllText(logPath, $"[{DateTime.Now}] Servicio detenido correctamente.\n");
            }
            catch (Exception ex)
            {
                EventLog.WriteEntry("Error al detener el servicio: " + ex.Message, EventLogEntryType.Error);
            }
        }

        string getValue(string prop)
        {

            string[] lines = File.ReadAllLines("application.properties");

            foreach (string line in lines)
            {
                string id = line.Split('=')[0];
                if (id.Trim().Equals(prop.Trim()))
                {
                    return line.Split('=')[1];
                }
            }

            return "";
        }
    }
}
