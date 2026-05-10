package com.example.parknow1.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://jkcemzvtyomuhizyqaxo.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImprY2VtenZ0eW9tdWhpenlxYXhvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzU1MTU5NzksImV4cCI6MjA5MTA5MTk3OX0.8v7-uqGb2WCduJkT00JtaElUmXrjSIQcYu02Ki84c_A"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}