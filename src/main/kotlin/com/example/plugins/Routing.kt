package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    val folder = File("src/main/resources/video/halo")
    routing {

        staticResources("/", "static") {
            default("index.html")
            preCompressed(CompressedFileType.GZIP)
        }

        staticResources("/index.mpd", "video/halo", index = "Halo.mpd") {
            contentType {
                ContentType.parse("application/dash+xml")
            }
        }

        get("/{fileName}") {
            val filename = call.parameters["fileName"]
            filename?.let {
                //val folder = File("src/main/resources/video/halo")
                val video = File(folder, filename)
                call.respondBytes(video.readBytes(), contentType = ContentType.parse("video/mp4"))
            }
        }
    }
}
