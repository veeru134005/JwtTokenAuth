package com.insigno.util;

public class DatabaseQueryConstant {

	public final static String fetchUserDetailsQuery = "SELECT sequence_number FROM customer_basic_details WHERE application_id=:applicationId AND tenant_id=:tenentId AND customer_id=:custId and customer_password=:custPassword";
	public final static String findUser = "SELECT * FROM customer_basic_details WHERE application_id=:applicationId AND tenant_id=:tenentId AND customer_id=:custId and customer_password=:custPassword";
	public final static String fetQueryForTOken = "select * from tokens_table where customer_sequence_number=:seqNo";

	public final static String updateToken = "UPDATE tokens_table SET is_token_expired=:is_token_expired,token_created_at=:token_created_at,token_details=:token_details,token_revoked_at=:token_revoked_at WHERE customer_sequence_number=:customer_sequence_number";

	public final static String deleteToken = "delete from table tokens_table where customer_sequence_number=:customer_sequence_number";

	public final static String createToken = "Insert into tokens_table(customer_sequence_number,token_type,token_details,token_expires_at,token_created_at,token_revoked_at,is_token_expired,is_long_lived_token) values(:customer_sequence_number,:token_type,:token_details,:token_expires_at,:token_created_at,:token_revoked_at,:is_token_expired,:is_long_lived_token)";

}
