package daxo.the.domain.model.media


data class BasicMediaCardList(
    val requestData: String,
    val listName: String,
    val cards: List<BasicMediaCard>,
)