package mate.academy

class FinancialService {
    companion object {
        private const val USD_TO_EUR_RATE = 0.93
        private const val USD_TO_GBP_RATE = 0.82
    }

    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ): String {
        return "Transferred ${amount.value} ${currencyCode.code} from ${source.number} " +
               "to ${destination.number}. Transaction ID: ${transactionId.id}"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val exchangeRate = getExchangeRate(fromCurrency, toCurrency)
        val convertedAmount = amount.value * exchangeRate
        return CurrencyAmount(convertedAmount)
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        return when {
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> USD_TO_EUR_RATE
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> USD_TO_GBP_RATE
            else -> 1.0
        }
    }
}

@JvmInline
value class CurrencyAmount(val value: Double) {
    init {
        require(value >= 0) { "Amount must be non-negative" }
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(code.matches(Regex("[A-Z]{3}"))) { "Currency code must be a 3-letter uppercase format" }
    }
}

@JvmInline
value class AccountNumber(val number: String) {
    init {
        require(number.matches(Regex("\\d{10}"))) { "Account number must be a 10-digit string" }
    }
}

@JvmInline
value class TransactionId(val id: String) {
    init {
        require(id.isNotEmpty()) { "Transaction ID must not be empty" }
    }
}
