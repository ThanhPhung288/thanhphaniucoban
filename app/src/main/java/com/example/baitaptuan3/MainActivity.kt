@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.baitaptuan3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// ============================================================
// 1. ACTIVITY
// ============================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

// ============================================================
// 2. DANH SÁCH CÁC THÀNH PHẦN (route = tên trang)
// ============================================================
data class UiItem(
    val route: String,
    val title: String,
    val desc: String,
    val group: String
)

val uiItems = listOf(
    UiItem("text", "Text", "Displays text", "Display"),
    UiItem("image", "Image", "Displays an image", "Display"),

    UiItem("textfield", "TextField", "Input field for text", "Input"),
    UiItem("password", "PasswordField", "Input field for passwords", "Input"),
    UiItem("toggle", "Toggle", "On/off switch", "Input"),
    UiItem("slider", "Slider", "Selects a value from a range", "Input"),
    UiItem("datepicker", "DatePicker", "Selects a date", "Input"),

    UiItem("column", "Column", "Arranges elements vertically", "Layout"),
    UiItem("row", "Row", "Arranges elements horizontally", "Layout"),

    UiItem("button", "Button", "Triggers an action when tapped", "Selection"),
    UiItem("checkbox", "Checkbox", "Selects one or multiple options", "Selection"),
    UiItem("radio", "RadioButton", "Selects a single option", "Selection"),
    UiItem("picker", "Picker", "Selects a value from a list", "Selection"),

    UiItem("navigation", "NavigationView", "Navigation container with title and back button", "Navigation"),
    UiItem("tabbar", "TabBar", "Switches between main sections", "Navigation"),
    UiItem("menu", "Menu", "Shows a menu with multiple actions", "Navigation"),

    UiItem("list", "List", "Displays a list of items", "Content"),
    UiItem("card", "Card", "Container with content and style", "Content"),
    UiItem("divider", "Divider", "Separates content into sections", "Content"),
)

// ============================================================
// 3. NAVIGATION
// ============================================================
@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    val back: () -> Unit = { nav.popBackStack() }

    NavHost(navController = nav, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen {
                nav.navigate("home") { popUpTo("welcome") { inclusive = true } }
            }
        }
        composable("home") { HomeScreen { route -> nav.navigate(route) } }
        composable("home") { HomeScreen { route -> nav.navigate(route) } }
        composable("text") { TextScreen(back) }
        composable("image") { ImageScreen(back) }
        composable("textfield") { TextFieldScreen(back) }
        composable("password") { PasswordScreen(back) }
        composable("toggle") { ToggleScreen(back) }
        composable("slider") { SliderScreen(back) }
        composable("datepicker") { DatePickerScreen(back) }
        composable("column") { ColumnScreen(back) }
        composable("row") { RowScreen(back) }
        composable("button") { ButtonScreen(back) }
        composable("checkbox") { CheckboxScreen(back) }
        composable("radio") { RadioScreen(back) }
        composable("picker") { PickerScreen(back) }
        composable("navigation") { NavigationViewScreen(back) { nav.navigate("nav_detail") } }
        composable("nav_detail") { NavDetailScreen(back) }
        composable("tabbar") { TabBarScreen(back) }
        composable("menu") { MenuScreen(back) }
        composable("list") { ListScreen(back) }
        composable("card") { CardScreen(back) }
        composable("divider") { DividerScreen(back) }
    }
}

// ============================================================
// 4. KHUNG CHUNG CHO MỖI TRANG (thanh tiêu đề + nút quay lại)
// ============================================================
@Composable
fun DetailScaffold(
    title: String,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = actions
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            content = content
        )
    }
}

// ============================================================
// 5. MÀN HÌNH DANH SÁCH (UI Components List)
// ============================================================
@Composable
fun HomeScreen(onItemClick: (String) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("UI Components List") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiItems.groupBy { it.group }.forEach { (group, list) ->
                item {
                    Text(
                        text = group,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(list) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFBBDEFB), RoundedCornerShape(10.dp))
                            .clickable { onItemClick(item.route) }
                            .padding(12.dp)
                    ) {
                        Text(item.title, fontWeight = FontWeight.Bold)
                        Text(item.desc, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// ============================================================
// 6. DISPLAY
// ============================================================
@Composable
fun TextScreen(onBack: () -> Unit) {
    DetailScaffold("Text Detail", onBack) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = buildAnnotatedString {
                    append("The ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick ") }
                    withStyle(SpanStyle(color = Color(0xFFE67E00), fontSize = 28.sp)) { append("Brown ") }
                    append("fox ")
                    withStyle(SpanStyle(letterSpacing = 4.sp)) { append("jumps ") }
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) { append("over ") }
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("the ") }
                    withStyle(SpanStyle(fontFamily = FontFamily.Cursive, fontSize = 14.sp)) { append("lazy ") }
                    append("dog.")
                },
                fontSize = 22.sp
            )
        }
    }
}

@Composable
fun ImageScreen(onBack: () -> Unit) {
    DetailScaffold("Images", onBack) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Ảnh 1
            Image(
                painter = painterResource(id = R.drawable.anhtruong),
                contentDescription = "Ảnh 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            // Link dạng chữ gạch dưới
            Text(
                text = "https://www.ut.edu.vn/anh1.png",
                fontSize = 18.sp,
                textDecoration = TextDecoration.Underline
            )

            Spacer(Modifier.height(16.dp))

            // Ảnh 2
            Image(
                painter = painterResource(id = R.drawable.anhtuoantruong),
                contentDescription = "Ảnh 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Text("In app", fontSize = 18.sp)
        }
    }
}

// ============================================================
// 7. INPUT
// ============================================================
@Composable
fun TextFieldScreen(onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    DetailScaffold("TextField", onBack) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Thông tin nhập") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text("Tự cập nhật dữ liệu theo textfield: $text", color = Color.Red, fontSize = 13.sp)
    }
}

@Composable
fun PasswordScreen(onBack: () -> Unit) {
    var pass by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }
    DetailScaffold("PasswordField", onBack) {
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Mật khẩu") },
            visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { show = !show }) { Text(if (show) "Ẩn" else "Hiện") }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text("Độ dài mật khẩu: ${pass.length} ký tự")
    }
}

@Composable
fun ToggleScreen(onBack: () -> Unit) {
    var on by remember { mutableStateOf(false) }
    DetailScaffold("Toggle", onBack) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = on, onCheckedChange = { on = it })
            Spacer(Modifier.width(12.dp))
            Text(if (on) "Đang BẬT" else "Đang TẮT")
        }
    }
}

@Composable
fun SliderScreen(onBack: () -> Unit) {
    var value by remember { mutableFloatStateOf(50f) }
    DetailScaffold("Slider", onBack) {
        Text("Giá trị: ${value.toInt()}", fontSize = 20.sp)
        Slider(
            value = value,
            onValueChange = { value = it },
            valueRange = 0f..100f
        )
    }
}

@Composable
fun DatePickerScreen(onBack: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    val dateText = state.selectedDateMillis?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            .apply { timeZone = TimeZone.getTimeZone("UTC") }
            .format(Date(it))
    } ?: "Chưa chọn"

    DetailScaffold("DatePicker", onBack) {
        Button(onClick = { showDialog = true }) { Text("Chọn ngày") }
        Spacer(Modifier.height(12.dp))
        Text("Ngày đã chọn: $dateText", fontSize = 18.sp)
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = { TextButton(onClick = { showDialog = false }) { Text("OK") } },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Hủy") } }
        ) {
            DatePicker(state = state)
        }
    }
}

// ============================================================
// 8. LAYOUT
// ============================================================
@Composable
fun ColumnScreen(onBack: () -> Unit) {
    DetailScaffold("Column Layout", onBack) {
        // Column xếp các phần tử theo chiều dọc
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(4) { i ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(56.dp)
                        .background(Color(0xFF4F86E8), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("Item ${i + 1}", color = Color.White) }
            }
        }
    }
}

@Composable
fun RowScreen(onBack: () -> Unit) {
    DetailScaffold("Row Layout", onBack) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            repeat(4) {
                // Row xếp các phần tử theo chiều ngang
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(Color(0xFFA0BCF2), Color(0xFF4F86E8), Color(0xFFA0BCF2)).forEach { c ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(c, RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// 9. SELECTION
// ============================================================
@Composable
fun ButtonScreen(onBack: () -> Unit) {
    var count by remember { mutableIntStateOf(0) }
    DetailScaffold("Button", onBack) {
        Text("Số lần bấm: $count", fontSize = 20.sp)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { count++ }) { Text("Button") }
            OutlinedButton(onClick = { count++ }) { Text("Outlined") }
            TextButton(onClick = { count = 0 }) { Text("Reset") }
        }
    }
}

@Composable
fun CheckboxScreen(onBack: () -> Unit) {
    val options = listOf("Kotlin", "Java", "Swift")
    val checked = remember { mutableStateListOf(false, false, false) }
    DetailScaffold("Checkbox", onBack) {
        options.forEachIndexed { i, name ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { checked[i] = !checked[i] }
            ) {
                Checkbox(checked = checked[i], onCheckedChange = { checked[i] = it })
                Text(name)
            }
        }
        Spacer(Modifier.height(12.dp))
        val selected = options.filterIndexed { i, _ -> checked[i] }
        Text("Đã chọn: ${if (selected.isEmpty()) "không có" else selected.joinToString()}")
    }
}

@Composable
fun RadioScreen(onBack: () -> Unit) {
    val options = listOf("Nam", "Nữ", "Khác")
    var selected by remember { mutableStateOf(options[0]) }
    DetailScaffold("RadioButton", onBack) {
        options.forEach { name ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { selected = name }
            ) {
                RadioButton(selected = selected == name, onClick = { selected = name })
                Text(name)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Bạn chọn: $selected")
    }
}

@Composable
fun PickerScreen(onBack: () -> Unit) {
    val options = listOf("Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng", "Cần Thơ", "Vĩnh Long")
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(options[1]) }
    DetailScaffold("Picker", onBack) {
        Box {
            OutlinedButton(onClick = { expanded = true }) { Text("$selected  ▾") }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { name ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = { selected = name; expanded = false }
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Đã chọn: $selected")
    }
}

// ============================================================
// 10. NAVIGATION
// ============================================================
@Composable
fun NavigationViewScreen(onBack: () -> Unit, onNext: () -> Unit) {
    DetailScaffold("NavigationView", onBack) {
        Text("Trang này có tiêu đề và nút quay lại (←) ở trên cùng.")
        Spacer(Modifier.height(12.dp))
        Button(onClick = onNext) { Text("Đi tới trang chi tiết →") }
    }
}

@Composable
fun NavDetailScreen(onBack: () -> Unit) {
    DetailScaffold("Trang chi tiết", onBack) {
        Text("Đây là trang được mở bằng navigate(). Bấm ← để quay lại.")
    }
}

@Composable
fun TabBarScreen(onBack: () -> Unit) {
    val tabs = listOf("Trang chủ", "Tìm kiếm", "Cá nhân")
    var selectedTab by remember { mutableIntStateOf(0) }
    DetailScaffold("TabBar", onBack) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { i, title ->
                Tab(
                    selected = selectedTab == i,
                    onClick = { selectedTab = i },
                    text = { Text(title) }
                )
            }
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nội dung tab: ${tabs[selectedTab]}", fontSize = 20.sp)
        }
    }
}

@Composable
fun MenuScreen(onBack: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var action by remember { mutableStateOf("Chưa chọn") }
    DetailScaffold(
        title = "Menu",
        onBack = onBack,
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOf("Chia sẻ", "Chỉnh sửa", "Xóa").forEach { name ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = { action = name; expanded = false }
                    )
                }
            }
        }
    ) {
        Text("Bấm dấu ⋮ ở góc phải trên cùng.")
        Spacer(Modifier.height(12.dp))
        Text("Hành động vừa chọn: $action", fontSize = 18.sp)
    }
}

// ============================================================
// 11. CONTENT
// ============================================================
@Composable
fun ListScreen(onBack: () -> Unit) {
    DetailScaffold("List", onBack) {
        LazyColumn {
            items(30) { i ->
                Text(
                    text = "Phần tử số ${i + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun CardScreen(onBack: () -> Unit) {
    DetailScaffold("Card", onBack) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Tiêu đề Card", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(8.dp))
                Text("Card là khung chứa nội dung có bo góc và đổ bóng.")
            }
        }
    }
}

@Composable
fun DividerScreen(onBack: () -> Unit) {
    DetailScaffold("Divider", onBack) {
        Text("Phần 1", fontSize = 18.sp)
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 2.dp)
        Text("Phần 2", fontSize = 18.sp)
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        Text("Phần 3", fontSize = 18.sp)
    }
}
@Composable
fun WelcomeScreen(onReady: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.uicomponents),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text("Jetpack Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = onReady,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("I'm ready")
        }
    }
}