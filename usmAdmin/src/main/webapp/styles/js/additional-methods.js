(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "./jquery.validate"], factory );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

$.validator.addMethod("alphanumeric", function(value, element) {
	return this.optional(element) || /^\w+$/i.test(value);
}, "Letters, numbers, and underscores only please");

$.validator.addMethod("numberslettersonly", function(value, element) {
			return this.optional(element) || /^[0-9A-Za-z]+$/i.test(value);
		}, "格式不正确，只允许字母和数字");

$.validator.addMethod("lettersonly", function(value, element) {
	return this.optional(element) || /^[a-z]+$/i.test(value);
}, "Letters only please");

$.validator.addMethod("nowhitespace", function(value, element) {
	return this.optional(element) || /^\S+$/i.test(value);
}, "No white space please");

$.validator.addMethod("phone", function(phone_number, element) {
	phone_number = phone_number.replace(/\s+/g, "");
	return this.optional(element) || phone_number.match(/^(\+)?(\d|-|\s)*$/);
}, "Please specify a valid phone number");

jQuery.validator.addMethod("stateUS", function(value, element, options) {
	var isDefault = typeof options === "undefined",
		caseSensitive = ( isDefault || typeof options.caseSensitive === "undefined" ) ? false : options.caseSensitive,
		includeTerritories = ( isDefault || typeof options.includeTerritories === "undefined" ) ? false : options.includeTerritories,
		includeMilitary = ( isDefault || typeof options.includeMilitary === "undefined" ) ? false : options.includeMilitary,
		regex;

	if (!includeTerritories && !includeMilitary) {
		regex = "^(A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|PA|RI|S[CD]|T[NX]|UT|V[AT]|W[AIVY])$";
	} else if (includeTerritories && includeMilitary) {
		regex = "^(A[AEKLPRSZ]|C[AOT]|D[CE]|FL|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEINOPST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$";
	} else if (includeTerritories) {
		regex = "^(A[KLRSZ]|C[AOT]|D[CE]|FL|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEINOPST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$";
	} else {
		regex = "^(A[AEKLPRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|PA|RI|S[CD]|T[NX]|UT|V[AT]|W[AIVY])$";
	}

	regex = caseSensitive ? new RegExp(regex) : new RegExp(regex, "i");
	return this.optional(element) || regex.test(value);
},
"Please specify a valid state");

$.validator.addMethod("zipcodeUS", function(value, element) {
	return this.optional(element) || /^\d{5}(-\d{4})?$/.test(value);
}, "The specified US ZIP Code is invalid");

}));

$.validator.addMethod("numberslettersonly-", function(value, element) {
	return this.optional(element) || /^[0-9 A-Za-z \-]+$/i.test(value);
}, "Letters,-, numbers only please");

$.validator.addMethod("letterSCHP", function(value, element) {
	return this.optional(element) || /^[\'\.\,\-\sa-zA-Z]+$/g.test(value);
}, "letters,spaces,commas,hyphens,periods only please");

$.validator.addMethod("andSoOn", function(value, element) {
	return this.optional(element) || !(/[^\s\(\)\[\]\{\}\*\&\$\^\%\#\@\!\~\`\,\.\;\:\'\"\\?\.\<\>\+\=\|\/\\\w_-]/g.test(value));								     
}, "Must be 50 characters or less");

$.validator.addMethod("passwordCheck", function(value, element) {
	return this.optional(element) ||/^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{7,8}$/.test(value);
}, "密码必须包含数字、字母，密码的长度为7-8个字符。");


/*邮箱校验*/
$.validator.addMethod("emailCheck", function(value, element) {
	return this.optional(element) ||/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value);
}, "请输入正确的邮箱格式");




$.validator.setDefaults({
    highlight: function(element) {
      $(element).closest('.form-group').removeClass('has-success');
        $(element).closest('.form-group').addClass('has-error');
    },
    unhighlight: function(element) {
        $(element).closest('.form-group').removeClass('has-error');
        $(element).closest('.form-group').addClass('has-success');
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function(error, element) {
        if(element.parent('.input-group').length) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }
});