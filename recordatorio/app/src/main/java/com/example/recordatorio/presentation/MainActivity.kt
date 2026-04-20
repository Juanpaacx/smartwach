package com.example.recordatorio.presentation

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.recordatorio.R
import com.example.recordatorio.presentation.theme.RecordatorioTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {

                AppNavigation2()
            }

    }
}

@Composable
fun AppNavigation2() {
    val navController = rememberNavController()
    val reminders = remember { mutableStateListOf<Reminder>() }

    NavHost(navController = navController, startDestination = "main_reminder_screen") {
        composable("main_reminder_screen") {
            MainReminderScreen(navController, reminders)
        }
        composable("add_reminder") {
            AddReminderScreen(navController, reminders)
        }
    }
}

@Composable
fun MainReminderScreen(navController: NavHostController, reminders: List<Reminder>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Reminders", fontSize = 15.sp)
        reminders.forEach { reminder ->
            Text(
                text = "${reminder.text} - ${if (reminder.time != null) "in ${reminder.time} seconds" else "No time set"}",
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 4.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { navController.navigate("add_reminder") },
            modifier = Modifier
                .width(100.dp)
                .height(30.dp),
        ) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun AddReminderScreen(navController: NavHostController, reminders: MutableList<Reminder>) {
    var reminderText by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add a Reminder", fontSize = 15.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reminderText,
            onValueChange = { reminderText = it },
            label = { Text("Reminder Text") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reminderTime,
            onValueChange = { reminderTime = it },
            label = { Text("Reminder Time (in seconds, optional)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val timeInSeconds = reminderTime.toLongOrNull()
                val reminder = Reminder(reminderText, timeInSeconds)
                reminders.add(reminder)
                timeInSeconds?.let {
                    scheduleReminder(reminderText, it, context)
                }
                navController.popBackStack()
            },
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
        ) {
            Text("Save")
        }
    }
}

fun scheduleReminder(reminderText: String, delayInSeconds: Long, context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(delayInSeconds, TimeUnit.SECONDS)
        .setInputData(workDataOf("reminderText" to reminderText, "reminderId" to reminderText.hashCode()))
        .build()
    WorkManager.getInstance(context).enqueue(workRequest)
}

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val reminderText = inputData.getString("reminderText") ?: return Result.failure()
        val reminderId = inputData.getInt("reminderId", 0)
        showNotification(reminderText, reminderId)
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(reminderText: String, reminderId: Int) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "reminder_channel"
        val channel = NotificationChannel(channelId, "Reminder Notifications", NotificationManager.IMPORTANCE_HIGH).apply {
            description = "Channel for reminder notifications"
        }
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Reminder")
            .setContentText(reminderText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(applicationContext).notify(reminderId, notification)
    }
}

data class Reminder(val text: String, val time: Long?)
