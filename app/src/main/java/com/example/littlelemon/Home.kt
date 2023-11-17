package com.example.littlelemon

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.util.Locale.Category


@Composable
fun Home(navController: NavHostController, database: AppDatabase) {

    val context = LocalContext.current

    val databaseMenuItems = database.menuItemDao().getAll().observeAsState(emptyList())

    val searchPhrase = remember {
        mutableStateOf("")
    }

    val categoryFilter = remember {
        mutableStateOf("")
    }

    var menuItems = if(categoryFilter.value.isEmpty()) {
        databaseMenuItems.value
    }
    else {
        databaseMenuItems.value.filter { it.category.contains(categoryFilter.value) }
    }

    menuItems = if(searchPhrase.value.isEmpty()) {
        menuItems
    }
    else {
        menuItems.filter { it.title.contains(searchPhrase.value, ignoreCase = true) }
    }

    val menuItemByCat = databaseMenuItems.value.groupBy { it.category }.keys.toList()

    Column {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    //.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Logo",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }
        Surface(
            color = Color(0xFF495E57),
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Little Lemon",
                    color = Color(0xFFf4CE14),
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                Row {
                    Column(
                        modifier = Modifier.fillMaxWidth(.6f)
                    ) {
                        Text(text = "Chicago",
                            color = Color(0xFFFFFFFF),
                            fontSize = 24.sp)
                        Text(text = stringResource(id = R.string.description),
                            color = Color(0xFFFFFFFF),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 20.dp))
                    }
                    Image(painter = painterResource(id = R.drawable.heroimage),
                        contentDescription = "Little Lemon Restaurant",
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.CenterVertically)
                            .clip(shape = RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillWidth)
                }
                OutlinedTextField(
                    value = searchPhrase.value,
                    onValueChange = {
                        searchPhrase.value = it
                    },
                    placeholder = { Text(text = "Enter Search Phrase")},
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFFFFF),
                        unfocusedContainerColor = Color(0xFFAFAFAF)
                    ),
                    maxLines = 1,
                    leadingIcon = { Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )}
                )
            }

        }
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            Text(text = "ORDER FOR DELIVERY!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(
                    items = menuItemByCat,
                    itemContent = {items ->
                        val buttonColor = remember {
                            mutableStateOf(Color(0xFFAFAFAF))
                        }
                        buttonColor.value = when(categoryFilter.value) {
                            items -> Color(0xFF495E57)
                            else -> Color(0xFFAFAFAF)
                        }
                        Button(
                            onClick = {
                                when (categoryFilter.value) {
                                    items -> categoryFilter.value = ""
                                    else -> categoryFilter.value = items
                                }
                            },
                            modifier = Modifier.wrapContentWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor.value
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = items.replaceFirstChar { it.uppercase() })
                        }
                    }
                )
            }
            Divider(
                color = Color(0xFFAFAFAF),
                thickness = 1.dp
            )
        }
        MenuItemList(menuItems = menuItems)
    }


}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemList(menuItems: List<MenuItemRoom>) {
    LazyColumn {
        items(
            items = menuItems,
            itemContent = { menuItems ->
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.75f)
                    ) {
                        Text(text = menuItems.title,
                            modifier = Modifier.padding(vertical = 5.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = menuItems.description,
                            color = Color(0xFF495E57)
                        )
                        Text(text = "$" + menuItems.price.toString(),
                            color = Color(0xFF495E57),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 5.dp))
                    }
                    GlideImage(model = menuItems.image, contentDescription = "Menu Item Logo",
                        modifier = Modifier.align(Alignment.CenterVertically))
                }
                Divider(
                    color = Color(0xFFAFAFAF),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        )
    }
}