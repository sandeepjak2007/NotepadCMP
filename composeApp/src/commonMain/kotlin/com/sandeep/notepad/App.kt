package com.sandeep.notepad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sandeep.notepad.ui.NavigationRoute.NoteList
import com.sandeep.notepad.ui.NavigationRoute.PdfView
import com.sandeep.notepad.ui.NoteAddEditScreen
import com.sandeep.notepad.ui.NoteListScreen
import com.sandeep.notepad.ui.NotepadViewModel
import com.sandeep.notepad.ui.PdfViewerScreen
import com.sandeep.notepad.ui.backgroundColor
import com.sandeep.notepad.ui.barColor
import com.sandeep.notepad.ui.bottomNavList
import com.sandeep.notepad.ui.funnelTypography
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun App() {
    MaterialTheme(
        typography = funnelTypography()
    ) {
        val navController = rememberNavController()
        val navViewModel = koinViewModel<NotepadViewModel>()
        val currentRoute by navController.currentBackStackEntryAsState()
        val snackBarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        containerColor = barColor,
                        contentColor = backgroundColor
                    )
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            when (currentRoute?.destination?.route) {
                                NoteList.route -> "Notes"
                                PdfView.route -> "PDF Viewer"
                                else -> "Notes"

                            }, fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = barColor,
                        titleContentColor = Color.White
                    )
                )
            },
            floatingActionButton = {
                if (currentRoute?.destination?.route == NoteList.route) {
                    FloatingActionButton(
                        onClick = { navController.navigate("note_edit?noteId=-1") },
                        containerColor = barColor,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Note")
                    }
                }
            },
            bottomBar = {
                if (currentRoute?.destination?.route === NoteList.route || currentRoute?.destination?.route === PdfView.route) {
                    NavigationBar(
                        containerColor = barColor,
                        contentColor = Color.White,
                        modifier = Modifier.fillMaxWidth().clip(
                            RoundedCornerShape(
                                topStartPercent = 30,
                                topEndPercent = 30
                            )
                        )
                    ) {
                        bottomNavList.forEach { screen ->
                            NavigationBarItem(
                                selected = currentRoute?.destination?.route == screen.route,
                                onClick = {
                                    navController.navigate(screen.route)
                                },
                                icon = {
                                    Icon(
                                        when (screen) {
                                            is NoteList -> Icons.AutoMirrored.Filled.Notes
                                            is PdfView -> Icons.Default.PictureAsPdf
                                        }, contentDescription = screen.route
                                    )
                                },
                                label = {
                                    Text(
                                        text = when (screen) {
                                            is NoteList -> "Notes"
                                            is PdfView -> "PDF Viewer"
                                        }
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = Color.White,
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NoteList.route,
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(innerPadding)
            ) {
                composable(
                    NoteList.route
                ) {
                    val notes by navViewModel.notes.collectAsStateWithLifecycle()
                    NoteListScreen(notes, snackBarHostState) { id ->
                        navController.navigate("note_edit?noteId=$id")
                    }
                }
                composable(
                    PdfView.route
                ) {
                    PdfViewerScreen()
                }
                composable(
                    "note_edit?noteId={noteId}",
                    arguments = listOf(navArgument(name = "noteId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    })
                ) { backStackEntry ->
                    val id = backStackEntry.savedStateHandle.getStateFlow("noteId", -1L)
                        .collectAsStateWithLifecycle(initialValue = -1L).value
                    println("Note ID: $id")
                    NoteAddEditScreen(
                        noteId = id,
                        viewModel = navViewModel,
                        onDone = { navController.navigate(NoteList.route) }
                    )
                }

            }
        }
    }
}