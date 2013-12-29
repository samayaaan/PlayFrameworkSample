package controllers;

import java.util.List;

import models.Message;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import static play.data.Form.*;

public class Application extends Controller {

	public static class SampleForm {
		public String message;
	}

	public static Result index() {
//        return ok(index.render("何か", new Form(SampleForm.class)));
		List<Message> datas = Message.find.all();
		return ok(index.render("データベースのサンプル", datas));
    }
	
	public static Result add() {
		Form<Message> f = new Form(Message.class);
		return ok(add.render("投稿フォーム", f));
	}
	
	public static Result create() {
		Form<Message> f = new Form(Message.class).bindFromRequest();
		if(!f.hasErrors()){
			Message data = f.get();
			data.save();
			return redirect("/");
		} else {
			return badRequest(add.render("ERROR", f));
		}
	}
	
	public static Result setitem(){
		Form<Message> f = new Form(Message.class);
		return ok(item.render("ID番号を入力。", f));
	}
	
	public static Result edit(){
		Form<Message> f = new Form(Message.class).bindFromRequest();
		if(!f.hasErrors()){
			Message obj = f.get();
			Long id = obj.id;
			obj = Message.find.byId(id);
			if(obj != null){
				f = new Form(Message.class).fill(obj);
				return ok(edit.render("ID=" + id + "の投稿を編集。", f));
			} else {
				return ok(item.render("ERROR:IDの投稿が見つかりません。", f));
			}
		} else {
			return ok(item.render("ERROR:入力に問題があります。", f));
		}
	}
	
	public static Result update(){
		Form<Message> f = new Form(Message.class).bindFromRequest();
		if(!f.hasErrors()){
			Message data = f.get();
			data.update();
			return redirect("/");
		} else {
			return ok(edit.render("ERROR:再度入力してください。", f));
		}
	}
//	public static Result send(){
//		Form<SampleForm> f = form(SampleForm.class).bindFromRequest();
//		if(!f.hasErrors()){
//		
//			SampleForm data = f.get();
//			String msg = "you typed: " + data.message;
//			return ok(index.render(msg,f));
//		} else {
//			return badRequest(index.render("ERROR", form(SampleForm.class)));
//		}
//		
//	}

}
