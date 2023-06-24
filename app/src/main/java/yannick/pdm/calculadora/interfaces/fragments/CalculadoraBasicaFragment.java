package yannick.pdm.calculadora.interfaces.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.EmptyStackException;
import java.util.Stack;

import yannick.pdm.calculadora.databinding.FragmentCalculadoraBasicaBinding;
import yannick.pdm.calculadora.model.Alerta;


public class CalculadoraBasicaFragment extends Fragment {


    private FragmentCalculadoraBasicaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalculadoraBasicaBinding.inflate(getLayoutInflater());


        binding.panel.setText("");

        binding.btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"0");
            }
        });

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"1");
            }
        });

        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"2");
            }
        });

        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"3");
            }
        });

        binding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"4");
            }
        });

        binding.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"5");
            }
        });

        binding.btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"6");
            }
        });

        binding.btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"7");
            }
        });

        binding.btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"8");
            }
        });

        binding.btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"9");
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"+");
            }
        });

        binding.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"-");
            }
        });

        binding.multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"×");
            }
        });

        binding.divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.panel.setText(binding.panel.getText()+"÷");
            }
        });

        binding.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = (binding.panel.getText()+"");
                binding.panel.setText(removeLast(newText));
            }
        });

        binding.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression = binding.panel.getText()+"";
                try{
                    Double result = executarCalculo(expression, getContext());
                    binding.panel.setText(result+"");
                }catch (IllegalArgumentException e){
                    Alerta.alertar(getContext(), "Erro", "Erro ao executar o cálculo: " + e.getMessage());

                }
            }
        });



        return binding.getRoot();
    }

    public static String removeLast(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        return texto.substring(0, texto.length() - 1);
    }

    public static double executarCalculo(String expressao, Context context) {
        try {
            expressao = expressao.replaceAll("\\s", ""); // Remover espaços em branco

            if (expressao.isEmpty()) {
                throw new IllegalArgumentException("A expressão não pode estar vazia.");
            }

            if (!expressao.matches("[0-9+\\-*/÷.()]+")) {
                throw new IllegalArgumentException("A expressão contém caracteres inválidos.");
            }

            double resultado = avaliarExpressao(expressao, context);
            return resultado;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao executar o cálculo: " + e.getMessage());
        }
    }

    private static double avaliarExpressao(String expressao, Context context) {
        try {
            Stack<Double> pilhaNumeros = new Stack<>();
            Stack<Character> pilhaOperadores = new Stack<>();

            for (int i = 0; i < expressao.length(); i++) {
                char caractere = expressao.charAt(i);

                if (caractere == '(') {
                    pilhaOperadores.push(caractere);
                } else if (Character.isDigit(caractere)) {
                    StringBuilder sb = new StringBuilder();
                    while (i < expressao.length() && (Character.isDigit(expressao.charAt(i)) || expressao.charAt(i) == '.')) {
                        sb.append(expressao.charAt(i));
                        i++;
                    }
                    i--;
                    double numero = Double.parseDouble(sb.toString());
                    pilhaNumeros.push(numero);
                } else if (caractere == '+' || caractere == '-' || caractere == '*' || caractere == '÷') {
                    while (!pilhaOperadores.empty() && possuiPrecedencia(caractere, pilhaOperadores.peek())) {
                        double resultado = executarOperacao(pilhaNumeros, pilhaOperadores, context);
                        pilhaNumeros.push(resultado);
                    }
                    pilhaOperadores.push(caractere);
                } else if (caractere == ')') {
                    while (!pilhaOperadores.empty() && pilhaOperadores.peek() != '(') {
                        double resultado = executarOperacao(pilhaNumeros, pilhaOperadores, context);
                        pilhaNumeros.push(resultado);
                    }
                    pilhaOperadores.pop(); // Remover o '(' correspondente
                }
            }

            while (!pilhaOperadores.empty()) {
                double resultado = executarOperacao(pilhaNumeros, pilhaOperadores, context);
                pilhaNumeros.push(resultado);
            }

            return pilhaNumeros.pop();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("A expressão contém operadores ou parênteses inválidos.");
        }
    }

    private static boolean possuiPrecedencia(char operador1, char operador2) {
        if (operador2 == '(' || operador2 == ')') {
            return false;
        }
        if ((operador1 == '*' || operador1 == '/') && (operador2 == '+' || operador2 == '-')) {
            return false;
        }
        return true;
    }

    private static double executarOperacao(Stack<Double> pilhaNumeros, Stack<Character> pilhaOperadores, Context context) {
        double numero2 = pilhaNumeros.pop();
        double numero1 = pilhaNumeros.pop();
        char operador = pilhaOperadores.pop();

        switch (operador) {
            case '+':
                return numero1 + numero2;
            case '-':
                return numero1 - numero2;
            case '*':
                return numero1 * numero2;
            case '÷':
                try{
                    double r = numero1/numero2;
                    return r;
                }catch (Exception e){
                    Alerta.alertar(context, "Erro", "Divisão por zero não é permitida.");
                }
            default:
                Alerta.alertar(context, "Erro", "Operador inválido: " + operador);
                throw new ArithmeticException("Divisão por zero não é permitida.");
        }
    }



}