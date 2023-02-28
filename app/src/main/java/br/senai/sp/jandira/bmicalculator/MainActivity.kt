package br.senai.sp.jandira.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.textclassifier.SelectionEvent
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicalculator.calculate.calculator
import br.senai.sp.jandira.bmicalculator.calculate.getBmiClassification
import br.senai.sp.jandira.bmicalculator.model.Client
import br.senai.sp.jandira.bmicalculator.model.Product
import br.senai.sp.jandira.bmicalculator.ui.theme.BMICalculatorTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BMICalculatorTheme {
                Column() {
                    CalculatorScreen();

                }
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CalculatorScreen() {

    var weightState by rememberSaveable{
        mutableStateOf("");
    }

    var heightState by rememberSaveable{
        mutableStateOf("");
    }

    var bmiState by rememberSaveable  {
        mutableStateOf("0.0")
    }

    var bmiClassification by rememberSaveable {
        mutableStateOf("");
    }

    val context = LocalContext.current.applicationContext;
    val context2 = LocalContext.current


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(id = R.drawable.bmi),
                    contentDescription = "",
                )
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 32.sp,
                    color = Color.Blue,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                )
            }

            Column(
                modifier = Modifier
                    .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                ) {
                    Text(text = stringResource(id = R.string.weight_label))
                    OutlinedTextField(value = weightState,
                        onValueChange = {Log.i("DSM2", it)
                                        weightState = it;
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                ) {
                    Text(text = stringResource(id = R.string.height_label))
                    OutlinedTextField(value = heightState,
                        onValueChange = {
                                        Log.i("DSM2", it)
                                        heightState = it;
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Button(

                    onClick = {
                        var w = weightState.toDouble();
                        var h = heightState.toDouble();
                        var bmi = calculator(w, h);
                              bmiState =  String.format("%.2f", bmi)
                            bmiClassification = getBmiClassification(bmi, context)
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                        shape = RoundedCornerShape(24.dp),

                ) {
                    Text(text = stringResource(id = R.string.button_calculate), fontSize = 16.sp, modifier = Modifier.padding(12.dp))
                }
            }

            Column() {
                Card(modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp),
                    backgroundColor = Color(59, 74, 232)) {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(id = R.string.your_score),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold);
                        Text(text = bmiState,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                        Text(text = bmiClassification,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold)
                        Row() {
                            Button(onClick = {
                                             heightState =  ""
                                            weightState = ""
                                            bmiState = "0.0"
                                            bmiClassification = ""
                            }, shape = RoundedCornerShape(12.dp)) {
                                Text(text = stringResource(id = R.string.reset))
                            }
                            Spacer(modifier = Modifier.width(32.dp))
                            Button(onClick = {
                                    val openOther = Intent(context2, SignUpActivity::class.java)
                                   context2.startActivity(openOther)},
                                shape = RoundedCornerShape(12.dp))  {
                                Text(text = stringResource(id = R.string.share))
                            }
                        }

                    }
                }
            }
            }
        }
        
    }
