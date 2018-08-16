//package demo.services.internalCall;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//
//@FeignClient(name = "plaid", url = "${localhost:8080}")
//
//public interface InternalPlaidCall {
//
//    @RequestMapping(method = RequestMethod.GET, value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
//    Transactions getTransactions();
//}
//
//
//
