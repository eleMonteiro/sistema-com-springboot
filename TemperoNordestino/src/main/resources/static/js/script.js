/**
 * 
 */

$('.quantidade').change(function() {
	var preco = parseFloat($(this).parent().parent().parent().find('.precoPrato').text());
	var quantidade = parseInt($(this).val());
	
	$(this).parent().parent().parent().find('.precoTotal').text((preco*quantidade).toFixed(2));
	
	var href = $(this).parent().parent().find('.btn').attr('href');
	href = ('href', href.substring(0, href.lastIndexOf("/")));
	href += "/"+quantidade;
	$(this).parent().parent().find('.btn').attr('href', href);
})

$('.endereco').change(function() {
	var preco = $(this).parent().parent().parent().find('.endereco').val();
	
	var href = $(this).parent().parent().find('.confirmar-pedido').attr('href');
	href = ('href', href.substring(0, href.lastIndexOf("/")));
	href += "/"+preco;
	$(this).parent().parent().find('.confirmar-pedido').attr('href', href);

	console.log(href)
})