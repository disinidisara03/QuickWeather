using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using FireSharp.Config;
using FireSharp.Response;
using FireSharp.Interfaces;
using FireSharp;

namespace WeatherDesktopClient
{
    public partial class Current_location : Form
    {
        public Current_location()
        {
            InitializeComponent();
        }

        IFirebaseConfig ifc = new FirebaseConfig()
        {
            AuthSecret = "dlz3G7CTTrM2gulsJGR2vmFtnzjNZykJU7dMhq2r",
            BasePath = "https://accurate-weather-cbcf2-default-rtdb.firebaseio.com/"
        };

        IFirebaseClient client;

        private void Current_location_Load(object sender, EventArgs e)
        {
            try
            {
                client = new FirebaseClient(ifc);
                MessageBox.Show("Connected");

            }
            catch (Exception)
            {

                MessageBox.Show("Error");
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Weather wth = new Weather()
            {
                location = textBox1.Text,
            };
            var set = client.Set("Location/" +textBox1.Text,wth);
            MessageBox.Show("data inserted successfully");
        }
    }
}
