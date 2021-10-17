package br.edu.ifpb.resource;

import br.edu.ifpb.domain.Serie;
import br.edu.ifpb.domain.Usuario;
import br.edu.ifpb.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/serie")
public class SerieResource {

    @Autowired
    private SerieRepository serieRepository;

    //CadastrarSerie
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String cadastrarSerie(@Valid Serie serie, Model model){
        Optional<Serie> retornoSerie =  serieRepository.findByNome(serie.getNome());
        if(retornoSerie.isPresent()){
            model.addAttribute("error", "serieAlreadyExists");
            return "page";
        }
        model.addAttribute("serieAdicionada", serie);
        serieRepository.save(serie);
        return "page";
    }

    //ListarSerie
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView listaSerie(@ModelAttribute("usuario") Usuario usuario){
        List<Serie> listaSeries =  serieRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("serie/page");
        modelAndView.addObject("listaSerie", listaSeries);
        modelAndView.addObject("usuario", usuario);
        return modelAndView;
    }


    //DeletarSerie
    @RequestMapping(value = "/deletar", method = RequestMethod.DELETE)
    public String deletarSerie(Integer id, Model model){

         Optional<Serie> serie = serieRepository.findById(id);

         if(!serie.isPresent()){
            model.addAttribute("error", "serieNoExists");
            return  "redirect:deletar";
         }
         serieRepository.delete( serie.get());
         model.addAttribute("sucesso", "deletadoComSucesso");
         return "page";
    }


}
