package com.example.parknow1.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://slnintrdclkxwgxqkowv.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNsbmludHJkY2xreHdneHFrb3d2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg0MzIxMDksImV4cCI6MjA5NDAwODEwOX0.H1DtsCkA62SteuMMnlaFzLYPdwcR1Nl_yMriplnx2nM"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}