package main

import (
	"github.com/gin-gonic/gin"
	"github.com/integrate/mock-service/internal/db"
	"github.com/integrate/mock-service/internal/handlers"
)

func main() {
	db.Connect()

	r := gin.Default()

	r.Any("/mock/:projectId/*path", handlers.HandleMockRequest)

	err := r.Run("4010")

	if err != nil {
		return
	}
}
