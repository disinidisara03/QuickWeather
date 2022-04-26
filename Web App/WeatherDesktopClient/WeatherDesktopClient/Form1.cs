using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WeatherDesktopClient
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            HelloWeatherDesktopService.WeatherServiceClient objService =
                new HelloWeatherDesktopService.WeatherServiceClient("NetTcpBinding_IWeatherService");

            label1.Text = objService.MyFunction();
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            current_location next = new current_location();
            next.Show();
            this.Hide();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Blogs next2 = new Blogs();
            next2.Show();
            this.Hide();
        }
    }
}
