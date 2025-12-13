package models

type Contract struct {
	ID          string                 `gorm:"type:uuid;primaryKey;"`
	ProjectID   string                 `gorm:"type:uuid"`
	Name        string                 `json:"name"`
	OpenAPISpec map[string]interface{} `gorm:"serializer:json;column:open_api_spec"`
}

func (Contract) TableName() string {
	return "contracts"
}
