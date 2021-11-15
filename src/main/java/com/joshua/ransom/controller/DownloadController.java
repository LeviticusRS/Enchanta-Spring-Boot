package com.joshua.ransom.controller;

import com.joshua.ransom.service.MixPanelService;
import com.joshua.ransom.util.Sessions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Joshua Ransom on 2/4/2020.
 */
@Controller
public class DownloadController {


	private final MixPanelService mixPanelService;

	public DownloadController(MixPanelService mixPanelService) {
		this.mixPanelService = mixPanelService;
	}

	@RequestMapping(value = "/download")
	public String downloadOptions(HttpServletRequest request) {
		mixPanelService.event("View DL", Sessions.getClientIp(request));
		return "download";
	}

	@RequestMapping(value = "/download/jar")
	public String downloadJar(HttpServletRequest request) {
		mixPanelService.event("Download", Sessions.getClientIp(request));
		return "redirect:https://dl.dropboxusercontent.com/1/view/qbhgcagatm4cdvp/Client.jar";
	}

	@RequestMapping(value = "/download/launcher-jar")
	public String downloadLauncherJar(HttpServletRequest request) {
		mixPanelService.event("Download", Sessions.getClientIp(request));
		return "redirect:https://dl.dropboxusercontent.com/1/view/zn5vic0cahzae3c/Launcher.jar";
	}

	@RequestMapping(value = "/download/mac")
	public String downloadMac(HttpServletRequest request) {
		mixPanelService.event("Download", Sessions.getClientIp(request));
		return "redirect:https://www.enchanta.net/clients/mac.app.zip";
	}

	@RequestMapping(value = "/download/msi")
	public String downloadMsi(HttpServletRequest request) {
		if (request.getHeader("User-Agent").toLowerCase().contains("wow64")) {
			return "redirect:/download/msi64";
		} else {
			return "redirect:/download/msi32";
		}
	}

	@RequestMapping(value = "/download/msi64")
	public String downloadMsi64(HttpServletRequest request) {
		mixPanelService.event("Download", Sessions.getClientIp(request));
		return "redirect:https://dl.dropboxusercontent.com/1/view/7w3ziu38rbjkkmd/Enchanta%20Launcher.zip";
	}

	@RequestMapping(value = "/download/msi32")
	public String downloadMsi32(HttpServletRequest request) {
		mixPanelService.event("Download", Sessions.getClientIp(request));
		return "redirect:https://dl.dropboxusercontent.com/1/view/7w3ziu38rbjkkmd/Enchanta%20Launcher.zip";
	}

}
