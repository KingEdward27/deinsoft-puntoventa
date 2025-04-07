using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.ServiceProcess;
namespace punto_venta_client_admin
{
    public partial class Form1 : Form
    {
        string SERVICE_NAME = "Punto de Venta Service";
        string FILE_NAME = "application.properties";
        private ServiceController servicio;
        public Form1()
        {
            InitializeComponent();
            servicio = new ServiceController(SERVICE_NAME); // Reemplaza con el nombre exacto de tu servicio
        }

        private void button3_Click(object sender, EventArgs e)
        {
        }
        string getValue(string prop)
        {

            string[] lines = File.ReadAllLines(FILE_NAME);

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

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                if (button2.Text.Equals("Start"))
                {
                    //using (System.ServiceProcess.ServiceController serviceController
                    //= new System.ServiceProcess.ServiceController(SERVICE_NAME))
                    //{
                    //    serviceController.Start();
                    //}


                    try
                    {
                        if (servicio.Status == ServiceControllerStatus.Stopped)
                        {
                            servicio.Start();
                            servicio.WaitForStatus(ServiceControllerStatus.Running, TimeSpan.FromSeconds(10));
                            //MessageBox.Show("Servicio iniciado.");
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error al iniciar el servicio: " + ex.Message);
                    }
                }
                else
                {
                   // using (System.ServiceProcess.ServiceController serviceController
                   //= new System.ServiceProcess.ServiceController(SERVICE_NAME))
                   // {
                   //     serviceController.Stop();
                   // }

                    try
                    {
                        if (servicio.Status == ServiceControllerStatus.Running)
                        {
                            servicio.Stop();
                            servicio.WaitForStatus(ServiceControllerStatus.Stopped, TimeSpan.FromSeconds(10));
                            MessageBox.Show("Servicio detenido.");
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error al detener el servicio: " + ex.ToString());
                    }
                }
            }
            catch (Exception ex)
            {
                //if (ex is Win32Exception || ex is InvalidOperationException)
                //{
                //    MessageBox.Show("Servicio no encontrado o Acceso denegado. Asegurese de ejecutar el aplicativo como Administrador");
                //}
                //else
                //{
                MessageBox.Show("Ocurrió un error inesperado: " + Environment.NewLine + ex.ToString());
                //}

            }
        }
        void verify()
        {
            //ServiceController sc = new ServiceController(SERVICE_NAME);
            string wa = "";
            switch (servicio.Status)
            {
                case ServiceControllerStatus.Running:
                    wa = "Running";
                    button2.Text = "Stop";
                    break;
                case ServiceControllerStatus.Stopped:
                    wa = "Stopped";
                    button2.Text = "Start";
                    break;
                case ServiceControllerStatus.Paused:
                    wa = "Paused";
                    button2.Text = "Start";
                    break;
                case ServiceControllerStatus.StopPending:
                    wa = "Stopping";
                    button2.Text = "Start";
                    break;
                case ServiceControllerStatus.StartPending:
                    wa = "Starting";
                    button2.Text = "Stop";
                    break;
                default:
                    wa = "Status Changing";
                    button2.Text = "Stop";
                    break;
            }
            label2.Text = wa;
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            timer1.Enabled = true;
            try
            {
                textBox1.Text = getValue("server.port").Trim();
            }
            catch (IOException)
            {
                MessageBox.Show("Archivo properties no encontrado");
            }
            catch (Exception)
            {
                MessageBox.Show("Propiedad server.port no encontrada");
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            verify();
        }
    }
}
