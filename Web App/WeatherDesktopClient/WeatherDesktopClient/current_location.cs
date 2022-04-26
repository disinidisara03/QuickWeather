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
    public partial class current_location : Form
    {
        public current_location()
        {
            InitializeComponent();
        }

        IFirebaseConfig ifc = new FirebaseConfig()
        {
            AuthSecret = "dlz3G7CTTrM2gulsJGR2vmFtnzjNZykJU7dMhq2r",
            BasePath = "https://accurate-weather-cbcf2-default-rtdb.firebaseio.com/"
        };

        IFirebaseClient client;

        private void current_location_Load(object sender, EventArgs e)
        {
            try
            {

                client = new FirebaseClient(ifc);
                MessageBox.Show("connected");
            }
            catch
            {
                MessageBox.Show("Error");
            }

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string text = "insert data" + textBox1.Text;
            Console.WriteLine("done" +textBox1.Text);
        }
    }
}
