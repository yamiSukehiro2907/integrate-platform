package handlers

import (
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/integrate/mock-service/internal/db"
	"github.com/integrate/mock-service/internal/models"
)

func HandleMockRequest(c *gin.Context) {
	projectID := c.Param("projectID")
	path := c.Param("path")

	var contract models.Contract

	result := db.DB.Select("id", "project_id", "open_api_spec").Where("project_id = ?", projectID).First(&contract)

	if result.Error != nil {
		c.JSON(http.StatusNotFound, gin.H{
			"error":      "Contract not found!",
			"project_id": projectID,
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message":        "Mock Service Hit!",
		"project_id":     "contract.ProjectID",
		"requested_path": path,
		"spec_found":     true,
	})
}

func generateMockResponse(path string) map[string]interface{} {
	data := make(map[string]interface{})
	path = strings.ToLower(path)
}
